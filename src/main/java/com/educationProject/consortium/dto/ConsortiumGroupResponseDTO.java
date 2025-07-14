package com.educationProject.consortium.dto;

import java.math.BigDecimal;

public record ConsortiumGroupResponseDTO(long id, String groupName, BigDecimal totalValue, int quotaQuantity, int monthQuantity) {

}
