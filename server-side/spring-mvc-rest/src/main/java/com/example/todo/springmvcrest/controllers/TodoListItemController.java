package com.example.todo.springmvcrest.controllers;

import com.example.todo.springmvcrest.models.DependencyModel;
import com.example.todo.springmvcrest.models.FilterModel;
import com.example.todo.springmvcrest.models.JsonResponseModel;
import com.example.todo.springmvcrest.models.TodoListItemModel;
import com.example.todo.springmvcrest.services.DependencyService;
import com.example.todo.springmvcrest.services.TodoListItemService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(TodoListItemController.BASE_URL)
public class TodoListItemController {

    public static final String BASE_URL = "/api/v1/todoListItem";
    private final TodoListItemService todoListItemService;
    private final DependencyService dependencyService;

    public TodoListItemController(TodoListItemService todoListItemService, DependencyService dependencyService) {
        this.todoListItemService = todoListItemService;
        this.dependencyService = dependencyService;
    }

    @GetMapping("/{todoId}")
    public JsonResponseModel getAllTodoItemsById(@PathVariable int todoId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        List<TodoListItemModel> todoListItemModels = todoListItemService.findAllTodoListItemById(todoId);
        jsonResponseModel.setSuccess(true);
        jsonResponseModel.getData().addAll(todoListItemModels);
        return jsonResponseModel;
    }

    @GetMapping("/{todoId}/notCompleted")
    public JsonResponseModel getAllTodoItemsByIdNotCompleted(@PathVariable int todoId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        List<TodoListItemModel> todoListItemModels = todoListItemService.findTodoListItemsNotComplete(todoId,1);
        jsonResponseModel.setSuccess(true);
        jsonResponseModel.getData().addAll(todoListItemModels);
        return jsonResponseModel;
    }

    @PostMapping
    public JsonResponseModel createTodoListItem(@RequestBody TodoListItemModel todoListItemModel) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        TodoListItemModel todoListItemModel1 = todoListItemService.createTodoListItem(todoListItemModel);
        if (todoListItemModel1 != null) {
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("Success");
            jsonResponseModel.getData().add(todoListItemModel1);
            System.out.println("Item Created");
        } else {
            jsonResponseModel.setSuccess(false);
            jsonResponseModel.setMsg("Failed");
        }
        return jsonResponseModel;
    }

    @RequestMapping(value = "complete", method = RequestMethod.POST)
    public JsonResponseModel markItemAsComplete(@RequestParam("todoItemId") int todoItemId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        List<DependencyModel> dependencyModels = dependencyService.getItemDependencies(todoItemId);
        if (dependencyModels.size() > 0) {
            jsonResponseModel.setSuccess(false);
            jsonResponseModel.setMsg("You can not complete this item until dependencies is completed.");
        } else {
            TodoListItemModel todoListItemModel = todoListItemService.updateItemState(todoItemId, 2);
            if (todoListItemModel != null) {
                jsonResponseModel.setSuccess(true);
                jsonResponseModel.setMsg("TO-DO item is completed.");
            }
        }
        return jsonResponseModel;
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public JsonResponseModel removeTodoListItem(@RequestParam("itemId") int itemId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        try {
            todoListItemService.removeTodoListItem(itemId);
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("TO-DO List item is removed.");
        } catch (Exception e) {
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("Operation failed");
        }
        return jsonResponseModel;
    }


    @RequestMapping(value = "{itemId}/dependencies", method = RequestMethod.POST)
    public JsonResponseModel addDependenciesForItem(@RequestBody List<Integer> dependencies, @PathVariable int itemId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        List<DependencyModel> dependencyModels = new ArrayList<>();
        for (Integer id : dependencies) {
            DependencyModel dependencyModel = new DependencyModel(id, itemId);
            dependencyModels.add(dependencyModel);
        }
        dependencyService.createDependency(dependencyModels);
        return jsonResponseModel;
    }

    @RequestMapping(value = "{listId}/filter", method = RequestMethod.POST)
    public JsonResponseModel filterListItems(@RequestBody FilterModel filterModel, @PathVariable int listId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        List<TodoListItemModel> itemList = todoListItemService.filterListItems(listId, filterModel.getNameQuery(), filterModel.getStateQuery());
        jsonResponseModel.setSuccess(true);
        jsonResponseModel.getData().addAll(itemList);
        return jsonResponseModel;
    }

}
