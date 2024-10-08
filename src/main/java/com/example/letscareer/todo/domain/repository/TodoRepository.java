package com.example.letscareer.todo.domain.repository;

import com.example.letscareer.todo.domain.model.Todo;
import com.example.letscareer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserUserId(Long userId);
    Optional<Todo> findByUserUserIdAndTodoId(Long userId, Long todoId);
    void deleteByTodoId(Long todoId);
}
