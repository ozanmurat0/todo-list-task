package com.example.todo.springmvcrest.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class DependencyModel {


    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private int id;

    //Holds which item, the item is dependent on
    private int dependecyId;

    //Holds the id of item that has dependency
    private int dependentId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;


    public DependencyModel() {
    }

    public DependencyModel(int dependecyId, int dependentId) {
        this.dependecyId = dependecyId;
        this.dependentId = dependentId;
    }
}
