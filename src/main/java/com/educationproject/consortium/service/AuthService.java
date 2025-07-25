package com.educationproject.consortium.service;

import org.springframework.stereotype.Service;

import com.educationproject.consortium.dto.AuthRequestDTO;
import com.educationproject.consortium.entity.User;
import com.educationproject.consortium.exception.ResourceNotFoundException;
import com.educationproject.consortium.repository.UserRepository;
import com.educationproject.consortium.util.JwtUtil;

@Service
public class AuthService {

	private final UserRepository repository;
	private final JwtUtil jwt;
	
	public AuthService(UserRepository repository, JwtUtil jwt) {
		this.repository = repository;
		this.jwt = jwt;
	}
	
	public String login(AuthRequestDTO request) {
		User u = repository.findByUsername(request.username()).orElseThrow(() -> new ResourceNotFoundException("User not found or Password incorrect."));
		if(!u.getPassword().equals(request.password())) throw new ResourceNotFoundException("User not found or Password incorrect.");
		return jwt.generateToken(request.username());
	}
}
