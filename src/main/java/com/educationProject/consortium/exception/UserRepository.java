package com.educationProject.consortium.exception;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educationProject.consortium.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
