package com.educationproject.consortium.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educationproject.consortium.dto.AuthRequestDTO;
import com.educationproject.consortium.dto.AuthResponseDTO;
import com.educationproject.consortium.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
		String response = service.login(request);
		return ResponseEntity.ok(new AuthResponseDTO(response));
	}
	
}

