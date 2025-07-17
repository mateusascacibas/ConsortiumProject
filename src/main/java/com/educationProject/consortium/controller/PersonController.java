package com.educationProject.consortium.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educationProject.consortium.entity.Person;
import com.educationProject.consortium.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService service;
	
	@PostMapping("")
	public ResponseEntity<String> create(@RequestBody Person p){
		Person person = service.createPerson(p);
		return ResponseEntity.created(URI.create("/person/"+person.getId())).body("Person created with success -> " + person);
	}
	
	@GetMapping("")
	public ResponseEntity<List<Person>> listAll(){
		List<Person> list = service.listAll();
		return ResponseEntity.ok(list);
	}
}
