package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.TodoListModel;
import com.example.todo.springmvcrest.repositories.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService {

    private final TodoListRepository todoListRepository;

    public TodoListServiceImpl(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Override
    public TodoListModel createTodoList(TodoListModel todoListModel) {
        return todoListRepository.save(todoListModel);
    }

    @Override
    public List<TodoListModel> findAllTodoLists(int userId) {
        return todoListRepository.findAllTodoListById(userId);
    }

    @Override
    public void removeTodoList(int todoId) {
        todoListRepository.deleteById(todoId);
    }
}
