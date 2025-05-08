package me.dionclei.workshopspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.entities.dto.OrderDTO;
import me.dionclei.workshopspringboot.repositories.UserRepository;
import me.dionclei.workshopspringboot.services.exceptions.DatabaseException;
import me.dionclei.workshopspringboot.services.exceptions.ResourceNotFoundException;


@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Cacheable("allUsers")
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Cacheable(value = "userById", key = "#id")
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Cacheable(value = "userByEmail", key = "#email")
	public User findByEmail(String email) {
		User user = userRepository.findUserByEmail(email);
		return user;
	}
	
	@CacheEvict(value = { "allUsers" }, allEntries = true)
	@Transactional
	public User insert(User obj) {
		return userRepository.save(obj);
	}
	
	@CacheEvict(value = { "allUsers", "userById", "userByEmail" }, allEntries = true)
	@Transactional
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@CachePut(value = "userById", key = "#id")
	@CacheEvict(value = { "allUsers", "userByEmail" }, allEntries = true)
	@Transactional
	public User update(Long id, User obj) {
		try {
			User entity = userRepository.getReferenceById(id);
			updateData(entity, obj);
			return userRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Cacheable(value = "userOrders", key = "#id")
	public List<OrderDTO> getOrders(Long id) {
		var u = userRepository.findById(id);
		User user = u.orElseThrow(() -> new ResourceNotFoundException(id));
		return user.toDTO().getOrders();
	}
	
	@Transactional
	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
	
}
