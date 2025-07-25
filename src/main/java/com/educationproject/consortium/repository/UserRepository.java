package com.educationproject.consortium.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educationproject.consortium.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
