/*
TodoService.java is called by the controller, and handles logic. Primarily, it calls CRUD operations via the TodoRepository
*/ 
package com.githubjon_steele.todoapi.service;

import com.githubjon_steele.todoapi.dto.CreateTodoRequest;
import com.githubjon_steele.todoapi.dto.UpdateTodoRequest;
import com.githubjon_steele.todoapi.entity.TodoEntity;
import com.githubjon_steele.todoapi.exception.TodoNotFoundException;
import com.githubjon_steele.todoapi.model.Todo;
import com.githubjon_steele.todoapi.repository.TodoRepository;

import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public List<Todo> findAll() {
		return todoRepository.findAll()
			.stream()
			.map(this::toModel)
			.toList();
	}

	public Todo findById(Long id) {
		return todoRepository.findById(id)
			.map(this::toModel)
			.orElseThrow(() -> new TodoNotFoundException(id));
	}

	public Todo create(CreateTodoRequest request) {
		TodoEntity todoEntity = new TodoEntity(null, request.title(), request.description(), false);
		return toModel(todoRepository.save(todoEntity));
	}

	public Todo update(Long id, UpdateTodoRequest request) {
		TodoEntity todoEntity = todoRepository.findById(id)
			.orElseThrow(() -> new TodoNotFoundException(id));
		todoEntity.setTitle(request.title());
		todoEntity.setDescription(request.description());
		todoEntity.setCompleted(request.completed());
		return toModel(todoRepository.save(todoEntity));
	}

	public void delete(Long id) {
		if (!todoRepository.existsById(id)) {
			throw new TodoNotFoundException(id);
		}
		todoRepository.deleteById(id);
	}

	private Todo toModel(TodoEntity todoEntity) {
		return new Todo(
			todoEntity.getId(),
			todoEntity.getTitle(),
			todoEntity.getDescription(),
			todoEntity.isCompleted()
		);
	}
}
