/*
Exception class for Todo request errors. 
*/
package com.githubjon_steele.todoapi.exception;

public class TodoNotFoundException extends RuntimeException {

	public TodoNotFoundException(Long id) {
		super("Todo with id " + id + " was not found");
	}
}
