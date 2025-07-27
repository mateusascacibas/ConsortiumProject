package com.educationProject.consortium.service;

import java.time.Duration;
import java.util.List;

import com.educationProject.consortium.producer.ConsortiumGroupProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.educationProject.consortium.dto.ConsortiumGroupRequestDTO;
import com.educationProject.consortium.dto.ConsortiumGroupResponseDTO;
import com.educationProject.consortium.entity.ConsortiumGroup;
import com.educationProject.consortium.exception.ResourceNotFoundException;
import com.educationProject.consortium.mapper.ConsortiumGroupMapper;
import com.educationProject.consortium.repository.ConsortiumGroupRepository;

@Service
public class ConsortiumGroupService {

    @Autowired
    private ConsortiumGroupRepository repository;

    @Autowired
    private ConsortiumGroupProducer producer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ConsortiumGroupService(ConsortiumGroupRepository repository,
                                  ConsortiumGroupProducer producer,
                                  RedisTemplate<String, Object> redisTemplate) {
        this.repository = repository;
        this.producer = producer;
        this.redisTemplate = redisTemplate;
    }

    public ConsortiumGroupResponseDTO create(ConsortiumGroupRequestDTO request) {
        ConsortiumGroup entity = repository.save(ConsortiumGroupMapper.toEntity(request));
        ConsortiumGroupResponseDTO response = ConsortiumGroupMapper.toDTO(entity);
        producer.sendMessage("Consortium with ID " + response.id() + " created successfully.");
        getOrCacheConsortiumGroup(response.id());
        return response;
    }

    public List<ConsortiumGroupResponseDTO> listAll() {
        String redisKey = "consortium_all";
        List<Object> cachedList = redisTemplate.opsForList().range(redisKey, 0, -1);
        if (cachedList != null && !cachedList.isEmpty()) {
            System.out.println("Returning list from Redis cache!");
            return cachedList.stream()
                    .map(obj -> (ConsortiumGroupResponseDTO) obj)
                    .toList();
        }
        List<ConsortiumGroupResponseDTO> freshList = repository.findAll().stream()
                .map(ConsortiumGroupMapper::toDTO)
                .toList();
        for (ConsortiumGroupResponseDTO dto : freshList) {
            redisTemplate.opsForList().rightPush(redisKey, dto);
        }
        redisTemplate.expire(redisKey, Duration.ofMinutes(10));

        System.out.println("Returning list from database and caching in Redis.");
        return freshList;
    }


    public ConsortiumGroupResponseDTO listById(long id) {
        ConsortiumGroup c = getConsortiumGroup(id);
        return ConsortiumGroupMapper.toDTO(c);
    }

    public ConsortiumGroupResponseDTO updateById(ConsortiumGroupRequestDTO request, long id) {
        ConsortiumGroup current = getConsortiumGroup(id);
        updateValues(request, current);
        ConsortiumGroup updated = repository.save(current);
        producer.sendMessage("Consortium with ID " + updated.getId() + " updated successfully.");
        removeCache("consortium_" + id);
        getOrCacheConsortiumGroup(id);
        return ConsortiumGroupMapper.toDTO(updated);
    }

    public void deleteById(long id) {
        ConsortiumGroup current = getConsortiumGroup(id);
        producer.sendMessage("Consortium with ID " + current.getId() + " deleted successfully.");
        removeCache("consortium_" + id);
        repository.delete(current);
    }

    private ConsortiumGroup getConsortiumGroup(long id) {
        ConsortiumGroup current = getOrCacheConsortiumGroup(id);
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

    private void removeCache(String key) {
        redisTemplate.delete(key);
    }

    public ConsortiumGroup getOrCacheConsortiumGroup(long id) {
        String key = "consortium_" + id;
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null && cached instanceof ConsortiumGroup) {
            System.out.println("Data coming from cache!");
            return (ConsortiumGroup) cached;
        }

        ConsortiumGroup entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consortium Group not found."));
        redisTemplate.opsForValue().set(key, entity, Duration.ofMinutes(10));
        System.out.println("Data coming from database and saved in cache!");
        return entity;
    }
}
