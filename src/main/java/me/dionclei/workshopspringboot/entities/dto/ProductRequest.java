package me.dionclei.workshopspringboot.entities.dto;

import java.util.Set;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
		@NotBlank(message = "Name can not be empty")
		@Size(min = 3, max = 20, message = "Name must be between 3 and 20") 
		String name, 

		@NotBlank(message = "Description can not be empty")
		@Size(min = 3, max = 100, message = "Description must be between 5 and 100") 
		String description, 	

		@NotNull
		@DecimalMin(value = "10.0", message = "Price must be greater than or equal to 10.0")
		@DecimalMax(value = "10000.0", message = "Price must be smaller than or equal to 10000.0") 
		Double price, 
		
		@NotNull
		Set<Long> categories) {
		
		
}
