package com.educationProject.consortium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConsortiumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsortiumApplication.class, args);
	}

}
