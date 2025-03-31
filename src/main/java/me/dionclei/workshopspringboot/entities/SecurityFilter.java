package me.dionclei.workshopspringboot.entities;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.dionclei.workshopspringboot.repositories.UserRepository;
import me.dionclei.workshopspringboot.services.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService service;
	@Autowired
	private UserRepository repository;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = request.getHeader("Authorization");
		if (token != null) {
			var subject = service.validateToken(token);
			UserDetails userDetails = repository.findByEmail(subject);
			var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null) return null;
		return authHeader.replace("bearer ", " ");
	}
	
	
	
}
