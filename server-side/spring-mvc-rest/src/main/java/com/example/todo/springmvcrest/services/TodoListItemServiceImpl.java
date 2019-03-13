package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.TodoListItemModel;
import com.example.todo.springmvcrest.repositories.DependencyRepository;
import com.example.todo.springmvcrest.repositories.TodoListItemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoListItemServiceImpl implements TodoListItemService {


    private final TodoListItemRepository todoListItemRepository;
    private final DependencyRepository dependencyRepository;

    public TodoListItemServiceImpl(TodoListItemRepository todoListItemRepository, DependencyRepository dependencyRepository) {
        this.todoListItemRepository = todoListItemRepository;
        this.dependencyRepository = dependencyRepository;
    }

    @Override
    public List<TodoListItemModel> findAllTodoListItemById(int todoId) {
        return todoListItemRepository.findByListId(todoId);
    }

    @Override
    public TodoListItemModel createTodoListItem(TodoListItemModel todoListItemModel) {
        return todoListItemRepository.save(todoListItemModel);
    }

    @Override
    public void removeTodoListItems(int listId) {
        List<TodoListItemModel> todoListItemModels = todoListItemRepository.findByListId(listId);
        List<Integer> ids = new ArrayList<Integer>();

        for (TodoListItemModel todoListItemModel : todoListItemModels) {
            ids.add(todoListItemModel.getId());
        }
        dependencyRepository.clearDependencies(ids);
        todoListItemRepository.deleteByListId(listId);
    }


    @Override
    public void removeTodoListItem(int itemId) {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(itemId);
        dependencyRepository.clearDependencies(ids);
        todoListItemRepository.deleteById(itemId);
    }

    @Override
    public TodoListItemModel updateItemState(int itemId, int state) {
        TodoListItemModel todoListItemModel = todoListItemRepository.findById(itemId);
        todoListItemModel.setState(state);
        dependencyRepository.deleteAllByDependecyId(itemId);
        return todoListItemRepository.save(todoListItemModel);
    }

    @Override
    public List<TodoListItemModel> filterListItems(int listId, String nameQuery, List<Integer> stateQuery) {
        return todoListItemRepository.findAllByListIdAndNameContainsAndStateIn(listId, nameQuery, stateQuery);
    }

    @Override
    @Scheduled(cron = "0 1 0 * * *")
    public void checkExpiredItems() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<Integer> ids = new ArrayList<Integer>();
        List<TodoListItemModel> todoListItemModels = todoListItemRepository.findAllByDeadlineBeforeAndStateEquals(date,1);
        for(TodoListItemModel item: todoListItemModels){
            item.setState(0);
            todoListItemRepository.save(item);
            ids.add(item.getId());
        }
        dependencyRepository.clearDependencies(ids);
        System.out.println("The cronjob is working to change expired list items status at  "+ dateFormat.format(date));
    }

    @Override
    public List<TodoListItemModel> findTodoListItemsNotComplete(int todoId, int state) {
        return todoListItemRepository.findAllByListIdAndState(todoId,state);
    }
}
