package me.dionclei.workshopspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.workshopspringboot.entities.Order;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.repositories.OrderRepository;
import me.dionclei.workshopspringboot.repositories.UserRepository;


@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> order = repository.findById(id);
		if (order.isPresent()) {
			return order.get();
		}
		return null;
	}
}
