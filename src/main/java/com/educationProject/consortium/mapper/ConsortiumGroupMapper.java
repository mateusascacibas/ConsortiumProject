package com.educationProject.consortium.mapper;

import com.educationProject.consortium.dto.ConsortiumGroupRequestDTO;
import com.educationProject.consortium.dto.ConsortiumGroupResponseDTO;
import com.educationProject.consortium.entity.ConsortiumGroup;

public class ConsortiumGroupMapper {
	public static ConsortiumGroupResponseDTO toDTO(ConsortiumGroup entity) {
		return new ConsortiumGroupResponseDTO(entity.getId(), entity.getGroupName(), entity.getTotalValue(), entity.getQuotaQuantity(), entity.getMonthQuantity());
	}
	
	public static ConsortiumGroup toEntity(ConsortiumGroupRequestDTO dto) {
		ConsortiumGroup c = new ConsortiumGroup();
		c.setGroupName(dto.groupName());
		c.setMonthQuantity(dto.monthQuantity());
		c.setQuotaQuantity(dto.quotaQuantity());
		c.setTotalValue(dto.totalValue());
		return c;
	}
}
