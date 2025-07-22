package com.educationproject.consortium.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.educationproject.consortium.dto.AuthRequestDTO;
import com.educationproject.consortium.entity.User;
import com.educationproject.consortium.exception.UserRepository;

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
