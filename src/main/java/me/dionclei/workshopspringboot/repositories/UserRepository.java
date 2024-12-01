package me.dionclei.workshopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.workshopspringboot.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
