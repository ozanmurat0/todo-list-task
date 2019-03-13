package com.example.todo.springmvcrest.controllers;


import com.example.todo.springmvcrest.models.JsonResponseModel;
import com.example.todo.springmvcrest.models.UserModel;
import com.example.todo.springmvcrest.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/v1/users";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<UserModel> getAllUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody
    //@GetMapping
    public UserModel getUserById(@RequestParam("userId") int userId){
        return userService.findUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JsonResponseModel createUser(@RequestBody UserModel userModel){
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        if(userService.checkUserExist(userModel.getEmail()) == null){
            UserModel userModel1 = userService.createUser(userModel);
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("Success");
            jsonResponseModel.getData().add(userModel1);
        }else{
            jsonResponseModel.setSuccess(false);
            jsonResponseModel.setMsg("Failed");
        }
        return jsonResponseModel;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonResponseModel loginUser(@RequestParam("email") String email,@RequestParam("password") String password){
        JsonResponseModel jsonResponseModel = new JsonResponseModel();
        UserModel userModel = userService.login(email,password);
        if(userModel != null){
            jsonResponseModel.setSuccess(true);
            jsonResponseModel.setMsg("Success");
            jsonResponseModel.getData().add(userModel);
        }else{
            jsonResponseModel.setSuccess(false);
            jsonResponseModel.setMsg("Failed");
        }
        return jsonResponseModel;
    }

 }
