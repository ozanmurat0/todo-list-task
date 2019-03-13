package com.example.mytodolist.service;

import com.example.mytodolist.models.FilterModel;
import com.example.mytodolist.models.TodoItemModel;
import com.example.mytodolist.models.TodoModel;
import com.example.mytodolist.models.UserModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyTodoService {

    @FormUrlEncoded
    @POST("users/login")
    Call<JsonObject> login(@Field("email") String email,
                           @Field("password") String password);

    @Headers({"Content-Type: application/json"})
    @POST("users")
    Call<JsonObject> createUser(@Body UserModel userModel);


    @Headers({"Content-Type: application/json"})
    @POST("todoList")
    Call<JsonObject> createTodoList(@Body TodoModel todoModel);

    @Headers({"Content-Type: application/json"})
    @POST("todoListItem")
    Call<JsonObject> createTodoListItem(@Body TodoItemModel todoItemModel);

    @GET("todoList")
    Call<JsonObject> getTodoLists(@Query("userId") int userId);

    @GET("todoListItem/{listId}")
    Call<JsonObject> getTodoListItems(@Path("listId") int listId);

    @GET("todoListItem/{listId}/notCompleted")
    Call<JsonObject> getTodoListItemsNotCompleted(@Path("listId") int listId);

    @DELETE("todoList/delete")
    Call<JsonObject> deleteTodoList(@Query("todoId") int todoId);

    @DELETE("todoListItem/delete")
    Call<JsonObject> deleteTodoListItem(@Query("itemId") int itemId);

    @Headers({"Content-Type: application/json"})
    @POST("todoListItem/{itemId}/dependencies")
    Call<JsonObject> addDependencies(@Body List<Integer> dependencies, @Path("itemId") int itemId);

    @FormUrlEncoded
    @POST("todoListItem/complete")
    Call<JsonObject> completeItem(@Field("todoItemId") int itemId);

    @Headers({"Content-Type: application/json"})
    @POST("todoListItem/{listId}/filter")
    Call<JsonObject> filterListItems(@Body FilterModel filterModel, @Path("listId") int listId);

}
