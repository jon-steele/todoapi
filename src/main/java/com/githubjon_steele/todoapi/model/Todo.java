package com.githubjon_steele.todoapi.model;

public record Todo(
	Long id,
	String title,
	String description,
	boolean completed
) {
}
