package com.educationProject.consortium.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.educationProject.consortium.entity.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
	Person findByName(String name);
}
