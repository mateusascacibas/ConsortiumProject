package com.educationProject.consortium.service;

import org.springframework.stereotype.Service;

import com.educationProject.consortium.dto.AuthRequestDTO;
import com.educationProject.consortium.entity.User;
import com.educationProject.consortium.exception.UserRepository;

@Service
public class UserService {

	private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public void create(AuthRequestDTO dto) {
    	User u = new User();
    	u.setUsername(dto.username());
    	u.setPassword(dto.password());
    	repository.save(u);
    }
}
