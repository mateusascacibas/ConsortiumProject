package com.educationproject.consortium.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educationproject.consortium.dto.AuthRequestDTO;
import com.educationproject.consortium.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody AuthRequestDTO dto){
		service.create(dto);
		return ResponseEntity.ok("User created with success.");
	}
}

