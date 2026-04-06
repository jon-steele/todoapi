/*
UpdateTodoRequest.java is a DTO used by the controller to handle JSON data from client for PUT requests
*/
package com.githubjon_steele.todoapi.dto;

public record UpdateTodoRequest(
	String title,
	String description,
	boolean completed
) {
}
