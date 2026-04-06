/*
UpdateTodoRequest.java is a DTO used by the controller to handle JSON data from client for POST requests
*/

package com.githubjon_steele.todoapi.dto;

public record CreateTodoRequest(
	String title,
	String description
) {
}
