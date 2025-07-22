package com.educationproject.consortium.mapper;

import com.educationproject.consortium.dto.PersonRequestDTO;
import com.educationproject.consortium.dto.PersonResponseDTO;
import com.educationproject.consortium.entity.Person;

public class PersonMapper {
    public static PersonResponseDTO toResponseDTO(Person person) {
        return new PersonResponseDTO(
            person.getId(),
            person.getName(),
            person.getAge(),
            person.getEmail(),
            person.getCity()
        );
    }

    public static Person toEntity(PersonRequestDTO requestDTO) {
        return new Person(
            requestDTO.name(),
            requestDTO.age(),
            requestDTO.email(),
            requestDTO.city()
        );
    }
}
