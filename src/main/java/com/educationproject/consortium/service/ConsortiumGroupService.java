package com.educationproject.consortium.service;

import java.time.Duration;
import java.util.List;

import com.educationproject.consortium.producer.ConsortiumGroupProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.educationproject.consortium.dto.ConsortiumGroupRequestDTO;
import com.educationproject.consortium.dto.ConsortiumGroupResponseDTO;
import com.educationproject.consortium.entity.ConsortiumGroup;
import com.educationproject.consortium.exception.ResourceNotFoundException;
import com.educationproject.consortium.mapper.ConsortiumGroupMapper;
import com.educationproject.consortium.repository.ConsortiumGroupRepository;

@Service
public class ConsortiumGroupService {

    @Autowired
    private ConsortiumGroupRepository repository;

    @Autowired
    private ConsortiumGroupProducer producer;

    @Autowired
    private RedisTemplate<String, ConsortiumGroup> redisTemplate;

    public ConsortiumGroupResponseDTO create(ConsortiumGroupRequestDTO request) {
        ConsortiumGroup entity = repository.save(ConsortiumGroupMapper.toEntity(request));
        ConsortiumGroupResponseDTO response = ConsortiumGroupMapper.toDTO(entity);
        producer.sendMessage("Consortium with ID " + response.id() + " created successfully.");
        cacheConsortiumGroup("consortium_" + response.id(), entity, 10);
        return response;
    }

    public List<ConsortiumGroupResponseDTO> listAll() {
        return repository.findAll().stream().map(ConsortiumGroupMapper::toDTO).toList();
    }

    @Cacheable(value = "consortiumGroup", key = "#id")
    public ConsortiumGroupResponseDTO listById(long id) {
        ConsortiumGroup c = getConsortiumGroup(id);
        return ConsortiumGroupMapper.toDTO(c);
    }

    public ConsortiumGroupResponseDTO updateById(ConsortiumGroupRequestDTO request, long id) {
        ConsortiumGroup current = getConsortiumGroup(id);
        updateValues(request, current);
        ConsortiumGroup updated = repository.save(current);
        producer.sendMessage("Consortium with ID " + updated.getId() + " updated successfully.");
        return ConsortiumGroupMapper.toDTO(updated);
    }

    public void deleteById(long id) {
        ConsortiumGroup current = getConsortiumGroup(id);
        producer.sendMessage("Consortium with ID " + current.getId() + " deleted successfully.");
        removeCache("consortium_" + id);
        repository.delete(current);
    }

    private ConsortiumGroup getConsortiumGroup(long id) {
        ConsortiumGroup current = getConsortiumGroupFromCache("consortium_" + id) != null ?
                getConsortiumGroupFromCache("consortium_" + id)
                : repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consortium Group not found."));
        return current;
    }

    private void updateValues(ConsortiumGroupRequestDTO request, ConsortiumGroup current) {
        if (request.groupName() != null && !request.groupName().isEmpty() && request.groupName() != current.getGroupName())
            current.setGroupName(request.groupName());
        if (request.monthQuantity() > 0 && request.monthQuantity() != current.getMonthQuantity())
            current.setMonthQuantity(request.monthQuantity());
        if (request.quotaQuantity() > 0 && request.quotaQuantity() != current.getQuotaQuantity())
            current.setQuotaQuantity(request.quotaQuantity());
        if (request.totalValue() != null && request.totalValue() != current.getTotalValue())
            current.setTotalValue(request.totalValue());

    }

    private void cacheConsortiumGroup(String key, ConsortiumGroup group, long ttMinutes) {
        redisTemplate.opsForValue().set(key, group, Duration.ofMinutes(ttMinutes));
    }

    private void removeCache(String key) {
        redisTemplate.delete(key);
    }

    private ConsortiumGroup getConsortiumGroupFromCache(String key) {
        return (ConsortiumGroup) redisTemplate.opsForValue().get(key);
    }
}
