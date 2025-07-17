package com.educationProject.consortium.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "person")
@Getter
@Setter
public class Person {
    
    @Id
    private String id;
    private String name;
    private int age;
}

