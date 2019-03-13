package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.UserModel;

import java.util.List;

public interface UserService {

    UserModel findUserById(int id);

    List<UserModel> findAllUsers();

    UserModel createUser(UserModel userModel);

    UserModel login(String email, String password);

    UserModel checkUserExist(String email);
}
