package com.example.todo.springmvcrest.repositories;

import com.example.todo.springmvcrest.models.TodoListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoListRepository  extends JpaRepository<TodoListModel, Integer> {

    @Query("Select td from TodoListModel td where td.user_id = ?1")
    List<TodoListModel> findAllTodoListById(int user_id);

    void deleteById(int id);
}
