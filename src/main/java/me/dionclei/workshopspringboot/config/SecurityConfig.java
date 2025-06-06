package me.dionclei.workshopspringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import me.dionclei.workshopspringboot.entities.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Bean
	SecurityFilterChain config(HttpSecurity security) throws Exception {
		return security.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "auth/register").permitAll()
						.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
						.requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")
						.requestMatchers("/orders/{id}").authenticated()
						.anyRequest().permitAll())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(e -> e.authenticationEntryPoint((request, response, ex) -> {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}))
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
