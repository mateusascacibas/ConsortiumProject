package com.educationProject.consortium.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educationProject.consortium.dto.ConsortiumGroupRequestDTO;
import com.educationProject.consortium.dto.ConsortiumGroupResponseDTO;
import com.educationProject.consortium.service.ConsortiumGroupService;

@RestController
@RequestMapping("/consortiumGroup/")
public class ConsortiumGroupController {
	
	@Autowired
	private ConsortiumGroupService service;
	
	@PostMapping("create")
	public ResponseEntity<String> create(@RequestBody ConsortiumGroupRequestDTO dto){
		ConsortiumGroupResponseDTO response = service.create(dto);
		URI location = URI.create("/consortiumList/" + response.id());
		return ResponseEntity.created(location).body("Consortium created with success -> " + response);
	}
	
	@GetMapping("/listAll")
	public List<ConsortiumGroupResponseDTO> listAll(){
		return service.listAll();
	}
	
	@GetMapping("/{id}")
	public ConsortiumGroupResponseDTO listById(@PathVariable long id) {
		return service.listById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateById(@RequestBody ConsortiumGroupRequestDTO dto, @PathVariable long id){
		ConsortiumGroupResponseDTO response = service.updateById(dto, id);
		return ResponseEntity.ok("Consortium updated with success -> " + response);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable long id){
		service.deleteById(id);
		return ResponseEntity.ok("Consortium deleted with success.");
	}
}
