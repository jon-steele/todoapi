package com.githubjon_steele.todoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TodoapiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void homeEndpointReturnsRunningMessage() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(content().string("Todo API is running"));
	}

	@Test
	void createTodoReturnsCreatedTodo() throws Exception {
		mockMvc.perform(post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "Write API",
					  "description": "Create the first todo endpoint"
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "http://localhost/api/todos/1"))
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.title").value("Write API"))
			.andExpect(jsonPath("$.description").value("Create the first todo endpoint"))
			.andExpect(jsonPath("$.completed").value(false));
	}

	@Test
	void getTodoByIdReturnsNotFoundWhenMissing() throws Exception {
		mockMvc.perform(get("/api/todos/99"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.status").value(404))
			.andExpect(jsonPath("$.error").value("Todo with id 99 was not found"));
	}

	@Test
	void updateTodoReplacesTodoFields() throws Exception {
		mockMvc.perform(post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "Old title",
					  "description": "Old description"
					}
					"""))
			.andExpect(status().isCreated());

		mockMvc.perform(put("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "New title",
					  "description": "New description",
					  "completed": true
					}
					"""))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.title").value("New title"))
			.andExpect(jsonPath("$.description").value("New description"))
			.andExpect(jsonPath("$.completed").value(true));
	}

	@Test
	void deleteTodoReturnsNoContent() throws Exception {
		mockMvc.perform(post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "Delete me",
					  "description": "Temporary todo"
					}
					"""))
			.andExpect(status().isCreated());

		mockMvc.perform(delete("/api/todos/1"))
			.andExpect(status().isNoContent());
	}

}
