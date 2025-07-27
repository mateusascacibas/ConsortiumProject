package com.educationProject.consortium.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PersonRequestDTO(@NotBlank(message = "Name is mandatory") String name, @Min(value = 1, message = "Age must be greater than 0") int age, @Email(message = "Invalid email") String email, @NotBlank(message = "City is mandatory") String city) {

}
