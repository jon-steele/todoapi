/*
Provides error handling for failed requests
*/
package com.githubjon_steele.todoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(TodoNotFoundException.class)
	public ResponseEntity<ApiError> handleTodoNotFound(TodoNotFoundException exception) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
}
