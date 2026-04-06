/*
Provides a JSON object for error cases
*/
package com.githubjon_steele.todoapi.exception;

public record ApiError(
	int status,
	String error
) {
}
