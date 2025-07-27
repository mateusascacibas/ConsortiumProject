package com.educationProject.consortium.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Document(collection = "person")
@Getter
@Setter
public class Person {
    
    @Id
    private String id;
    private String name;
    private int age;
    private String email;
    @Indexed
    private String city;

    public Person(String name, int age, String email, String city) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.city = city;
    }
}

