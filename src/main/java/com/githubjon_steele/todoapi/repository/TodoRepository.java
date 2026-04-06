/*
TodoRepository extends JpaRepository, providing methods of interfacing with database (H2)
*/

package com.githubjon_steele.todoapi.repository;

import com.githubjon_steele.todoapi.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
