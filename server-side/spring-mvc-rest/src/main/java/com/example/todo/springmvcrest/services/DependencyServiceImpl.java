package com.example.todo.springmvcrest.services;

import com.example.todo.springmvcrest.models.DependencyModel;
import com.example.todo.springmvcrest.repositories.DependencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService {


    private final DependencyRepository dependencyRepository;

    public DependencyServiceImpl(DependencyRepository dependencyRepository) {
        this.dependencyRepository = dependencyRepository;
    }

    @Override
    public List<DependencyModel> createDependency(List<DependencyModel> dependencyModels) {
        return dependencyRepository.saveAll(dependencyModels);
    }

    @Override
    public void clearDependencies(int dependencyId) {

    }

    @Override
    public List<DependencyModel> getItemDependencies(int dependentId) {
        return dependencyRepository.findByDependentId(dependentId);
    }

    @Override
    public void completeItemDependencies(int dependencyId) {
        dependencyRepository.deleteAllByDependecyId(dependencyId);
    }
}
