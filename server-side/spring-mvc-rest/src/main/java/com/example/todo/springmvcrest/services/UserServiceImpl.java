package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.UserModel;
import com.example.todo.springmvcrest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel findUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public UserModel login(String email, String password) {
      return userRepository.login(email,password);
    }

    @Override
    public UserModel checkUserExist(String email) {
        return userRepository.checkUserExist(email);
    }
}
