package me.dionclei.workshopspringboot.resources;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.workshopspringboot.entities.Order;
import me.dionclei.workshopspringboot.entities.dto.OrderDTO;
import me.dionclei.workshopspringboot.services.OrderService;
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService service;
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll() {
		List<Order> order = service.findAll();
		List<OrderDTO> orderDTO = order.stream().map(Order::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(orderDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok().body(service.findById(id).toDTO());
	}
	
}
