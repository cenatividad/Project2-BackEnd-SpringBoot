package com.revature.aspects;

import java.time.LocalDate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.revature.models.User;

@Aspect
@Component
public class JwtAspect {
	private static final String JWT_COOKIE_NAME = "scrum_jwt";
	private static final String JWT_USER_ID_CLAIM_NAME = "userID";
	
	@Autowired
	JWTVerifier verifier;
	
	@Autowired
	Algorithm algorithm;
	
	@Autowired
	Builder builder;
	
	/**
	 * Method that will extract and return the JWT encoded string from the request cookie.
	 * If the cookie name is not found in the request, method returns null
	 * @param request
	 * @return
	 */
	private String getJwtCookieValue(HttpServletRequest request) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(JWT_COOKIE_NAME)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Method that will attempt to decode the provided JSON Web Token string.
	 * Returns the Decoded JSON Web Token if successful.
	 * Throws HttpClientErrorException on failure.
	 * @param jwtString
	 * @return
	 */
	private DecodedJWT validateJwt(String jwtString) {
		try {
			return verifier.verify(jwtString);
		} catch (JWTVerificationException e) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Bad JWT");
		}
	}
	
	/**
	 * Method to build and sign a new JSON Web Token
	 * @param userID
	 * @return
	 */
	private String buildJwt(int userID) {
		LocalDate curDate = LocalDate.now();
		LocalDate expiryDate = LocalDate.of(curDate.getYear(), curDate.getMonthValue(), curDate.getDayOfMonth() + 7);
		
		try {
			return builder.withExpiresAt(java.sql.Date.valueOf(expiryDate))
				.withClaim(JWT_USER_ID_CLAIM_NAME, userID)
				.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create JWT");
		}
	}
	
	/**
	 * Method called to extract JSON Web Token from appropriate cookie and verify it.
	 * The decoded JSON Web Token is returned.
	 * If the JSON Web Token cannot be verified, an HttpClientErrorException will be thrown.
	 * @return
	 */
	private DecodedJWT verifyJwt() {
		String jwtValue = this.getJwtCookieValue(getHttpServletRequest());
		if (jwtValue != null) {
			return this.validateJwt(jwtValue);
		}
		throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized access: Please log in.");
	}
	
	/**
	 * method to get the current HttpServletRequest reference
	 * @return
	 */
	private HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	/**
	 * method to get the current HttpServletResponse reference
	 * @return
	 */
	private HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
	}
	
	/**
	 * Advice to execute on all service methods except for UserService.loginUser.
	 * Will attempt to verify valid JWT in request cookie before method execution.
	 * If any problem in verification, the advice will throw an appropriate
	 * HttpClientErrorException instead of executing the method.
	 * @param pjp
	 * @return Object reference that the method would normally return.
	 * @throws Throwable
	 */
	@Around("!execution(* com.revature.service.UserService.loginUser(..)) && "
			+ "!execution(* com.revature.service.UserService.createUser(..)) && "
			+ "execution(* com.revature.service.UserService.*(..)) ||"
			+ "execution(* com.revature.service.ProjectService.*(..)) ||"
			+ "execution(* com.revature.service.StoryService.*(..))")
	public Object verifyJwt(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println(pjp.getSignature());

		this.verifyJwt();
		
		Object returnVal = pjp.proceed();
		return returnVal;
	}
	
	/**
	 * Advice to act on successful return from loginUser method.
	 * 
	 * Will create JSON Web Token and add it to response cookie.
	 * @param user The User object reference that is being returned by the loginUser method
	 */
	@AfterReturning(pointcut="execution(* com.revature.controller.UserController.loginUser(..))",returning="user")
	public void setJwtCookie(Object user) {
		if (user != null && user instanceof User) {
			HttpServletResponse response = this.getHttpServletResponse();
			Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, buildJwt(((User)user).getUserID()));
			jwtCookie.setPath("/");
			response.addCookie(jwtCookie);
		}
	}
}
