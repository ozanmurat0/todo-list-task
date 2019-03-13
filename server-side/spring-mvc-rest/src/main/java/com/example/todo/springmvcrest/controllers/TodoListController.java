package com.example.todo.springmvcrest.controllers;

import com.example.todo.springmvcrest.models.JsonResponseModel;
import com.example.todo.springmvcrest.models.TodoListModel;
import com.example.todo.springmvcrest.services.TodoListItemService;
import com.example.todo.springmvcrest.services.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TodoListController.BASE_URL)
public class TodoListController {

    public static final String BASE_URL = "/api/v1/todoList";

    private final TodoListService todoListService;
    private final TodoListItemService todoListItemService;

    public TodoListController(TodoListService todoListService, TodoListItemService todoListItemService) {
        this.todoListService = todoListService;
        this.todoListItemService = todoListItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JsonResponseModel createTodoList(@RequestBody TodoListModel todoListModel) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        if (todoListService.createTodoList(todoListModel) != null) {
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("TO-DO list created.");
        } else {
            jsonResponseModel.setSuccess(false);
            jsonResponseModel.setMsg("TO-DO list does not created.");
        }
        return jsonResponseModel;
    }


    @GetMapping
    public JsonResponseModel getAllTodoListOfUser(@RequestParam("userId") int userId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        jsonResponseModel.setSuccess(true);
        jsonResponseModel.getData().addAll(todoListService.findAllTodoLists(userId));
        return jsonResponseModel;
    }


    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public JsonResponseModel deleteTodoList(@RequestParam("todoId") int todoId) {
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        try {
            todoListItemService.removeTodoListItems(todoId);
            todoListService.removeTodoList(todoId);
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("TO-DO list is removed");
        }catch (Exception e){
            jsonResponseModel.setSuccess(false);
            jsonResponseModel.setMsg("TO-DO list is not removed");
        }
        return jsonResponseModel;
    }
}
