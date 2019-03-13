package com.example.todo.springmvcrest.repositories;

import com.example.todo.springmvcrest.models.TodoListItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TodoListItemRepository extends JpaRepository<TodoListItemModel, Integer> {

    List<TodoListItemModel> findByListId(int listId);

    List<TodoListItemModel> deleteByListId(int listId);

    void deleteById(int itemId);

    TodoListItemModel findById(int itemId);

    List<TodoListItemModel> findAllByListIdAndNameContainsAndStateIn(int listId, String nameQuery, List<Integer> stateQuery);

    List<TodoListItemModel> findAllByDeadlineBeforeAndStateEquals(Date date, int state);

    List<TodoListItemModel> findAllByListIdAndState(int listId, int state);
}
