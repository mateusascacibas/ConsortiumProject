package com.educationproject.consortium.dto;

public record PersonResponseDTO(
    String id,
    String name,
    int age,
    String email,
    String city
) {
}
