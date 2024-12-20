package me.dionclei.workshopspringboot.entities.dto;

import me.dionclei.workshopspringboot.enums.UserRole;

public record RegisterRequest(String name, String email, String phone, String password) {
	
}
