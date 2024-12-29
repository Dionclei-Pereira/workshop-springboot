package me.dionclei.workshopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import me.dionclei.workshopspringboot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	UserDetails findByEmail(String email);
	
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);
	
}
