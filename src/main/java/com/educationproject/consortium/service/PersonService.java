package com.educationProject.consortium.service;

import java.util.List;

import com.educationProject.consortium.dto.PersonRequestDTO;
import com.educationProject.consortium.dto.PersonResponseDTO;
import com.educationProject.consortium.exception.PersonNotFoundException;
import com.educationProject.consortium.mapper.PersonMapper;
import org.springframework.stereotype.Service;

import com.educationProject.consortium.entity.Person;
import com.educationProject.consortium.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public PersonResponseDTO createPerson(PersonRequestDTO dto) {
        Person person = PersonMapper.toEntity(dto);
        return PersonMapper.toResponseDTO(repository.save(person));
    }

    public List<PersonResponseDTO> listAll() {
        return repository.findAll().stream().map(PersonMapper::toResponseDTO).toList();
    }

    public PersonResponseDTO findById(String id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return PersonMapper.toResponseDTO(person);
    }

    public PersonResponseDTO findByName(String name) {
        Person person = repository.findByName(name);
        if (person == null) {
            throw new RuntimeException("Person not found with name: " + name);
        }
        return PersonMapper.toResponseDTO(person);
    }

    public PersonResponseDTO updatePerson(PersonRequestDTO dto, String id) {
        Person existingPerson = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        existingPerson.setName(dto.name());
        existingPerson.setAge(dto.age());
        existingPerson.setEmail(dto.email());
        existingPerson.setCity(dto.city());

        return PersonMapper.toResponseDTO(repository.save(existingPerson));
    }

    public void deletePerson(String id) {
        Person existingPerson = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        repository.delete(existingPerson);
    }

    public List<PersonResponseDTO> listByCity(String city) {
        List<Person> persons = repository.findByCity(city);
        if (persons.isEmpty()) {
            throw new RuntimeException("No persons found in city: " + city);
        }
        return persons.stream().map(PersonMapper::toResponseDTO).toList();
    }

    public List<PersonResponseDTO> listByAgeGreatest(int age) {
        List<Person> persons = repository.findByAgeGreaterThan(age);
        if (persons.isEmpty()) {
            throw new RuntimeException("No persons found with age greater than: " + age);
        }
        return persons.stream().map(PersonMapper::toResponseDTO).toList();
    }

    public List<PersonResponseDTO> listByPartialName(String partialName) {
        List<Person> persons = repository.findByNameContaining(partialName);
        if (persons.isEmpty()) {
            throw new RuntimeException("No persons found with partial name: " + partialName);
        }
        return persons.stream().map(PersonMapper::toResponseDTO).toList();
    }
}
