package com.educationProject.consortium.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ConsortiumGroupRequestDTO(
		@NotBlank(message = "Group Name is required")
		String groupName, 
		
		@NotBlank(message = "Total value is required")
		@DecimalMin(value = "0,01", message = "Total value must be positive")
		BigDecimal totalValue, 
		
		@Min(value = 1, message = "Quota quantity must be at least 1")
		int quotaQuantity, 
		
		@Min(value = 1, message = "Month quantity must be at least 1")
		int monthQuantity
		) {}
