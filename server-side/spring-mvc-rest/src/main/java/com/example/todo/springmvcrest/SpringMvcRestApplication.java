package com.example.todo.springmvcrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringMvcRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcRestApplication.class, args);
    }

}
