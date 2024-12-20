package me.dionclei.workshopspringboot.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.entities.dto.AuthenticationRequest;
import me.dionclei.workshopspringboot.entities.dto.RegisterRequest;
import me.dionclei.workshopspringboot.enums.UserRole;
import me.dionclei.workshopspringboot.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthenticationRecourse {
	
	@Autowired
	UserRepository repository;
	@Autowired
	private AuthenticationManager manager;
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody @Valid AuthenticationRequest data) {
		var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.manager.authenticate(userPassword);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
		if(repository.findByEmail(request.email()) != null) return ResponseEntity.badRequest().build();
		if(request.password().length() < 6 || request.password().length() > 16) return ResponseEntity.badRequest().build();
		User u = new User(null, request.name(), request.email(), request.phone(), request.password(), UserRole.USER);
		repository.save(u);
		return ResponseEntity.ok().build();
	}

}
