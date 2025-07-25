package com.educationproject.consortium.controller;

import java.net.URI;
import java.util.List;

import com.educationproject.consortium.dto.PersonRequestDTO;
import com.educationproject.consortium.dto.PersonResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.educationproject.consortium.entity.Person;
import com.educationproject.consortium.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@PostMapping("")
	public ResponseEntity<String> create(@RequestBody @Valid PersonRequestDTO dto){
		PersonResponseDTO person = service.createPerson(dto);
		return ResponseEntity.created(URI.create("/person/"+person.id())).body("Person created with success -> " + person);
	}

	@Autowired
	private PersonService service;

	@GetMapping("")
	public ResponseEntity<List<PersonResponseDTO>> listAll(){
		List<PersonResponseDTO> list = service.listAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonResponseDTO> findById(@PathVariable String id){
		PersonResponseDTO person = service.findById(id);
		return ResponseEntity.ok(person);
	}

	@GetMapping("/byName/{name}")
	public ResponseEntity<PersonResponseDTO> findByName(@PathVariable String name){
		PersonResponseDTO person = service.findByName(name);
		return ResponseEntity.ok(person);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> update(@RequestBody PersonRequestDTO dto, @PathVariable String id){
		PersonResponseDTO person = service.updatePerson(dto, id);
		return ResponseEntity.ok("Person updated with success -> " + person);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id){
		service.deletePerson(id);
		return ResponseEntity.ok("Person deleted with success.");
	}

	@GetMapping("/byCity/{city}")
	public ResponseEntity<List<PersonResponseDTO>> listByCity(@PathVariable String city){
		List<PersonResponseDTO> persons = service.listByCity(city);
		return ResponseEntity.ok(persons);
	}

	@GetMapping("/byAge/{age}")
	public ResponseEntity<List<PersonResponseDTO>> listByAgeGreatest(@PathVariable int age) {
		List<PersonResponseDTO> persons = service.listByAgeGreatest(age);
		return ResponseEntity.ok(persons);
	}

	@GetMapping("/partialName/{partialName}")
	public ResponseEntity<List<PersonResponseDTO>> listByPartialName(@PathVariable String partialName) {
		List<PersonResponseDTO> persons = service.listByPartialName(partialName);
		return ResponseEntity.ok(persons);
	}
}
