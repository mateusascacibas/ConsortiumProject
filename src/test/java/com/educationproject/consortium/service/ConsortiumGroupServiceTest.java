package com.educationproject.consortium.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.educationproject.consortium.producer.ConsortiumGroupProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.educationproject.consortium.dto.ConsortiumGroupRequestDTO;
import com.educationproject.consortium.dto.ConsortiumGroupResponseDTO;
import com.educationproject.consortium.entity.ConsortiumGroup;
import com.educationproject.consortium.exception.ResourceNotFoundException;
import com.educationproject.consortium.mapper.ConsortiumGroupMapper;
import com.educationproject.consortium.repository.ConsortiumGroupRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class ConsortiumGroupServiceTest {

	@InjectMocks
	private ConsortiumGroupService service;
	
	@Mock
	private ConsortiumGroupRepository repository;

	@Mock
	private RedisTemplate<String, ConsortiumGroup> redisTemplate;

	@Mock
	private ValueOperations<String, ConsortiumGroup> valueOperations;

	@Mock
	private ConsortiumGroupProducer producer;


	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		service = new ConsortiumGroupService(repository, producer, redisTemplate);
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	}
	
	@Test
	void shouldCreateConsortiumGroup() {
		ConsortiumGroupRequestDTO request = new ConsortiumGroupRequestDTO("Test", new BigDecimal(20), 10, 20);
		ConsortiumGroup entity = ConsortiumGroupMapper.toEntity(request);
		entity.setId(1L);
		
		when(repository.save(any(ConsortiumGroup.class))).thenReturn(entity);
		ConsortiumGroupResponseDTO response = service.create(request);
		assertNotNull(response);
		assertEquals(1L, response.id());
		assertEquals("Test", response.groupName());
		verify(repository, times(1)).save(any(ConsortiumGroup.class));
	}
	
	@Test
	void shouldListAll() {
		List<ConsortiumGroup> list = new ArrayList<ConsortiumGroup>();
		ConsortiumGroup r1 = new ConsortiumGroup();
		r1.setId(1L);
		r1.setGroupName("T1");
		ConsortiumGroup r2 = new ConsortiumGroup();
		r2.setId(2L);
		r2.setGroupName("T2");
		list.add(r1);
		list.add(r2);
		when(repository.findAll()).thenReturn(list);
		List<ConsortiumGroupResponseDTO> response = service.listAll();
		assertNotNull(response);
		assertEquals(1L, response.get(0).id());
		assertEquals("T1", response.get(0).groupName());
		assertEquals(2L, response.get(1).id());
		assertEquals("T2", response.get(1).groupName());
		verify(repository, times(1)).findAll();

	}
	
	@Test
	void shouldListById() {
		ConsortiumGroup r1 = new ConsortiumGroup();
		r1.setId(1L);
		r1.setGroupName("T1");
		when(repository.findById(1L)).thenReturn(Optional.of(r1));
		ConsortiumGroupResponseDTO response = service.listById(1L);
		assertNotNull(response);
		assertEquals("T1", response.groupName());
        assertThrows(ResourceNotFoundException.class, () -> service.listById(2L));
        verify(repository, times(2)).findById(any(Long.class));
	}
	
	@Test
	void shouldUpdateById() {
		ConsortiumGroup r1 = new ConsortiumGroup();
		r1.setId(1L);
		r1.setGroupName("T1");
		r1.setTotalValue(new BigDecimal(1));
		r1.setMonthQuantity(1);
		r1.setQuotaQuantity(1);
		
		ConsortiumGroupRequestDTO request = new ConsortiumGroupRequestDTO("T2", new BigDecimal(1), 2, 2);
		ConsortiumGroup r2 = ConsortiumGroupMapper.toEntity(request);
		r2.setId(1L);
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(r1));
		when(repository.save(any(ConsortiumGroup.class))).thenReturn((r2));
		ConsortiumGroupResponseDTO response = service.updateById(request, 1L);
		assertNotNull(response);
		assertNotEquals("T1", response.groupName());
		assertEquals(new BigDecimal(1), response.totalValue());
		verify(repository, times(1)).findById(any(Long.class));
		verify(repository, times(1)).save(any(ConsortiumGroup.class));
	}
	
	@Test
	void shouldDeleteById() {
		ConsortiumGroup r1 = new ConsortiumGroup();
		r1.setId(1L);
		r1.setGroupName("T1");
		r1.setTotalValue(new BigDecimal(1));
		r1.setMonthQuantity(1);
		r1.setQuotaQuantity(1);
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(r1));
		service.deleteById(1L);
		verify(repository, times(1)).findById(any(Long.class));
		verify(repository, times(1)).delete(any(ConsortiumGroup.class));
	}
	
}
