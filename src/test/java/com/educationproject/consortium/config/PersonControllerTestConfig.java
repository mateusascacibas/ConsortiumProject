package com.educationproject.consortium.config;

import com.educationproject.consortium.producer.ConsortiumGroupProducer;
import com.educationproject.consortium.service.PersonService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class PersonControllerTestConfig {

    @Bean
    public PersonService personService() {
        return Mockito.mock(PersonService.class);
    }

    @Bean
    public ConsortiumGroupProducer consortiumGroupProducer() {
        return Mockito.mock(ConsortiumGroupProducer.class);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        return Mockito.mock(RedisTemplate.class);
    }
}

