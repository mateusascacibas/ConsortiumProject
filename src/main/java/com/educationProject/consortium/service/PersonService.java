package com.educationProject.consortium.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.educationProject.consortium.entity.Person;
import com.educationProject.consortium.repository.PersonRepository;

@Service
public class PersonService {

	private final PersonRepository repository;
	
	public PersonService(PersonRepository repository) {
		this.repository = repository;
	}
	
	public Person createPerson(Person p) {
		return repository.save(p);
	}
	
	public List<Person> listAll(){
		return repository.findAll();
	}
}
