package com.example.todo.springmvcrest.services;


import com.example.todo.springmvcrest.models.TodoListItemModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoListItemService {

    List<TodoListItemModel> findAllTodoListItemById(int todoId);

    TodoListItemModel createTodoListItem(TodoListItemModel todoListItemModel);

    @Transactional
    void removeTodoListItems(int todoId);

    @Transactional
    void removeTodoListItem(int itemId);

    TodoListItemModel updateItemState(int itemId, int state);

    List<TodoListItemModel> filterListItems(int listId, String nameQuery, List<Integer> stateQuery);

    void checkExpiredItems();

    List<TodoListItemModel> findTodoListItemsNotComplete (int todoId, int state);
}
