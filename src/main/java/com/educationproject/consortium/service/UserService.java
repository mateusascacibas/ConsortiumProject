package com.educationProject.consortium.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.educationProject.consortium.dto.AuthRequestDTO;
import com.educationProject.consortium.entity.User;
import com.educationProject.consortium.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public void create(AuthRequestDTO dto) {
    	Optional<User> existUser = repository.findByUsername(dto.username());
    	if(existUser.isPresent()) return;
    	User u = new User();
    	u.setUsername(dto.username());
    	u.setPassword(dto.password());
    	repository.save(u);
    }
}
