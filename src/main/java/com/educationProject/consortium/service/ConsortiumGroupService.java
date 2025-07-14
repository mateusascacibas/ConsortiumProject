package com.educationProject.consortium.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public ConsortiumGroupResponseDTO create(ConsortiumGroupRequestDTO request) {
		return ConsortiumGroupMapper.toDTO(repository.save(ConsortiumGroupMapper.toEntity(request)));
	}
	
	public List<ConsortiumGroupResponseDTO> listAll(){
		return repository.findAll().stream().map(ConsortiumGroupMapper::toDTO).toList();
	}
	
	public ConsortiumGroupResponseDTO listById(long id) {
		ConsortiumGroup c = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consortium Group not found."));
		return ConsortiumGroupMapper.toDTO(c);
	}
	
	public ConsortiumGroupResponseDTO updateById(ConsortiumGroupRequestDTO request, long id) {
		ConsortiumGroup current = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consortium Group not found."));
		updateValues(request, current);
		ConsortiumGroup updated = repository.save(current);
		return ConsortiumGroupMapper.toDTO(updated);
	}
	
	public void deleteById(long id) {
		ConsortiumGroup current = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Consortium Group not found."));
		repository.delete(current);
	}
	
	private void updateValues(ConsortiumGroupRequestDTO request, ConsortiumGroup current) {
		if(request.groupName() != null && !request.groupName().isEmpty()  && request.groupName() != current.getGroupName()) current.setGroupName(request.groupName());
		if(request.monthQuantity() > 0 && request.monthQuantity() != current.getMonthQuantity()) current.setMonthQuantity(request.monthQuantity());
		if(request.quotaQuantity() > 0 && request.quotaQuantity() != current.getQuotaQuantity()) current.setQuotaQuantity(request.quotaQuantity());
		if(request.totalValue() != null && request.totalValue() != current.getTotalValue()) current.setTotalValue(request.totalValue());
		
	}
}
