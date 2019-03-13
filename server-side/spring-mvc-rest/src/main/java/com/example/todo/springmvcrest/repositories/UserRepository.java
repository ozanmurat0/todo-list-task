package com.example.todo.springmvcrest.repositories;

import com.example.todo.springmvcrest.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query("Select u from UserModel u where u.email = ?1 and u.password = ?2 ")
    UserModel login(String email, String password);

    @Query("Select u from UserModel u where u.email = ?1")
    UserModel checkUserExist(String email);
}
