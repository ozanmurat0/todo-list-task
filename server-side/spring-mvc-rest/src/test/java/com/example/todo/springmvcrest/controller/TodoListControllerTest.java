package com.example.todo.springmvcrest.controller;

import com.example.todo.springmvcrest.AbstractTest;
import com.example.todo.springmvcrest.models.JsonResponseModel;
import com.example.todo.springmvcrest.models.TodoListModel;
import com.example.todo.springmvcrest.services.TodoListService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class TodoListControllerTest extends AbstractTest {

    public static final String BASE_URL = "/api/v1/todoList";

    @Autowired
    private TodoListService todoListService;


    @Before
    public void init(){
        TodoListModel todoListModel1 = new TodoListModel();
        todoListModel1.setName("list1");
        todoListModel1.setUser_id(1);
        TodoListModel todoListModel2 = new TodoListModel();
        todoListModel2.setName("list2");
        todoListModel2.setUser_id(1);
        TodoListModel todoListModel3 = new TodoListModel();
        todoListModel3.setName("list3");
        todoListModel3.setUser_id(1);
        todoListService.createTodoList(todoListModel1);
        todoListService.createTodoList(todoListModel2);
        todoListService.createTodoList(todoListModel3);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void deleteTodoList() throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/delete").param("todoId", "8")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JsonResponseModel jsonResponseModel = super.mapFromJson(content, JsonResponseModel.class);
        assertTrue(jsonResponseModel.isSuccess());
    }


    @Test
    public void getTodoList()  throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/?userId=1").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        JsonResponseModel jsonResponseModel = super.mapFromJson(content, JsonResponseModel.class);
        assertTrue(jsonResponseModel.getData().size() > 0);
    }

    @Test
    public void createTodoList() throws Exception{
        TodoListModel todoListModel = new TodoListModel();
        todoListModel.setName("list4");
        todoListModel.setUser_id(1);

        String inputJson = super.mapToJson(todoListModel);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        JsonResponseModel jsonResponseModel = super.mapFromJson(content, JsonResponseModel.class);
        assertTrue(jsonResponseModel.isSuccess());
    }
}
