package com.educationproject.consortium.mapper;

import com.educationproject.consortium.dto.ConsortiumGroupRequestDTO;
import com.educationproject.consortium.dto.ConsortiumGroupResponseDTO;
import com.educationproject.consortium.entity.ConsortiumGroup;

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
