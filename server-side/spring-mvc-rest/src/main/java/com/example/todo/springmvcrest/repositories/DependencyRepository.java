package com.example.todo.springmvcrest.repositories;

import com.example.todo.springmvcrest.models.DependencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface DependencyRepository extends JpaRepository<DependencyModel, Integer> {

    List<DependencyModel> findByDependentId(int dependentId);

    @Transactional
    @Modifying
    void deleteAllByDependecyId(int dependencyId);


    @Transactional
    @Modifying
    @Query("Delete from DependencyModel dp where dp.dependentId in (?1) or dp.dependecyId in (?1)")
    void clearDependencies(List<Integer> itemIds);

}
