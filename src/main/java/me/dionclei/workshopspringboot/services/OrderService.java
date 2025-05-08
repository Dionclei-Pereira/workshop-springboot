package me.dionclei.workshopspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.workshopspringboot.entities.Order;
import me.dionclei.workshopspringboot.repositories.OrderRepository;
import me.dionclei.workshopspringboot.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {
	
	private OrderRepository repository;
	
	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}
	
	@Cacheable("allOrders")
	@Transactional(readOnly = true)
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	@Cacheable(value = "orderById", key = "#id")
	@Transactional(readOnly = true)
	public Order findById(Long id) {
		Optional<Order> order = repository.findById(id);
		if (order.isPresent()) {
			return order.get();
		}
		throw new ResourceNotFoundException(id);
	}
	
	@CacheEvict(value = { "allOrders" }, allEntries = true)
	public void save(Order obj) {
		repository.save(obj);
	}
}
