package com.revature.tests.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.controller.UserController;
import com.revature.models.Project;
import com.revature.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	private MockMvc mockMvc;

	@Mock
	private UserService mockFetchService;

	@InjectMocks
	private UserController fetchController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(fetchController).build();
	}

	@Test
	public void GetUserProjectsOK() throws Exception {
		int id = 16;
		List<Project> returnedProjects = new ArrayList<Project>();
		returnedProjects.add(new Project(1, null, "name", "desc"));
		returnedProjects.add(new Project(2, null, "name2", "desc2"));

		when(mockFetchService.getUserProjects(id)).thenReturn(returnedProjects);

		this.mockMvc.perform(get("/users/" + id + "/projects"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith("application/json"));
	}

	@Test
	public void GetUserProjectsWrongId() throws Exception {
		int id = 20;
		HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

		when(mockFetchService.getUserProjects(id))
			.thenThrow(new HttpClientErrorException(expectedStatus, ""));

		this.mockMvc.perform(get("/users/" + id + "/projects"))
			.andDo(print())
			.andExpect(status().is(expectedStatus.value()));
	}
	
	@Test
	public void GetUserProjectsWrongParameters() throws Exception {
		int id = 20;
		HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
		
		when(mockFetchService.getUserProjects(id))
			.thenThrow(new HttpClientErrorException(expectedStatus, ""));
			
		this.mockMvc.perform(get("/users/" + id + "/projects")).andDo(print())
			.andExpect(status().is(expectedStatus.value()));
	}
}
