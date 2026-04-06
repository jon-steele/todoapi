/*
TodoController.java is used to map HTTP GET, POST, PUT, and DELETE requests to the TodoService
*/

package com.githubjon_steele.todoapi.controller;

import com.githubjon_steele.todoapi.dto.CreateTodoRequest;
import com.githubjon_steele.todoapi.dto.UpdateTodoRequest;
import com.githubjon_steele.todoapi.model.Todo;
import com.githubjon_steele.todoapi.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping
	public List<Todo> getAllTodos() {
		return todoService.findAll();
	}

	@GetMapping("/{id}")
	public Todo getTodoById(@PathVariable Long id) {
		return todoService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Todo> createTodo(@RequestBody CreateTodoRequest request) {
		Todo todo = todoService.create(request);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(todo.id())
			.toUri();
		return ResponseEntity.created(location).body(todo);
	}

	@PutMapping("/{id}")
	public Todo updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest request) {
		return todoService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
		todoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
