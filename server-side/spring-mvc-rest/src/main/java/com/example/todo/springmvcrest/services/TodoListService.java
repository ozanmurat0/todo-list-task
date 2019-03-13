package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.TodoListModel;

import java.util.List;

public interface TodoListService {

    TodoListModel createTodoList(TodoListModel todoListModel);

    List<TodoListModel> findAllTodoLists(int userId);

    void removeTodoList(int todoId);


}
