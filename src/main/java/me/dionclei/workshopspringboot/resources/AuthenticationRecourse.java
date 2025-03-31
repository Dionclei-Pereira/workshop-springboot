package me.dionclei.workshopspringboot.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.entities.dto.AuthenticationRequest;
import me.dionclei.workshopspringboot.entities.dto.LoginResponse;
import me.dionclei.workshopspringboot.entities.dto.RegisterRequest;
import me.dionclei.workshopspringboot.enums.UserRole;
import me.dionclei.workshopspringboot.repositories.UserRepository;
import me.dionclei.workshopspringboot.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationRecourse {
	
	private UserRepository repository;
	private AuthenticationManager manager;
	private TokenService service;
	
	public AuthenticationRecourse(UserRepository repository, AuthenticationManager manager, TokenService service) {
		this.repository = repository;
		this.manager = manager;
		this.service = service;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequest data) {
		var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.manager.authenticate(userPassword);
		var token = service.generateToken((User) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponse(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
		if(repository.findByEmail(request.email()) != null) return ResponseEntity.badRequest().body("Email must be unique");
		if(request.password().length() < 6 || request.password().length() > 16) return ResponseEntity.badRequest().build();
		User u = new User(null, request.name(), request.email(), request.phone(), request.password(), UserRole.USER);
		repository.save(u);
		return ResponseEntity.ok().build();
	}

}
