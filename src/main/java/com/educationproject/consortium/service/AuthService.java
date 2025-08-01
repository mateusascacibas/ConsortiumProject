package com.educationProject.consortium.service;

import org.springframework.stereotype.Service;

import com.educationProject.consortium.dto.AuthRequestDTO;
import com.educationProject.consortium.entity.User;
import com.educationProject.consortium.exception.ResourceNotFoundException;
import com.educationProject.consortium.repository.UserRepository;
import com.educationProject.consortium.util.JwtUtil;

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
