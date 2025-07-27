package com.educationProject.consortium.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.educationProject.consortium.entity.Person;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
	Person findByName(String name);
	List<Person> findByCity(String city);
	List<Person> findByAgeGreaterThan(int age);
	List<Person> findByNameContaining(String partialName);
	@Query("{ 'email' : { $regex: ?0 , $options: 'i'} }")
	List<Person> findBySpecifyEmail(String domain);
	@Query("{ 'city' : { $in: ?0 } }")
	List<Person> findByListCity(List<String> cities);
	@Query("{ 'city':?0, 'age: { gte: ?1}'")
	List<Person> findByCityAndMinAge(String city, int minAge);
	@Query(value = "{}", fields = "{ name: 1, email: 1 }")
	List<Person> findAllNameAndEmail();
}
