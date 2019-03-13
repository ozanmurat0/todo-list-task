package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.DependencyModel;

import java.util.List;

public interface DependencyService {

    List<DependencyModel> createDependency(List<DependencyModel> dependencyModel);

    void clearDependencies(int itemId);

    List<DependencyModel> getItemDependencies(int dependentId);

    void completeItemDependencies(int itemId);

}
