package com.educationProject.consortium.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ConsortiumGroupResponseDTO(long id, String groupName, BigDecimal totalValue, int quotaQuantity, int monthQuantity) {

}
