package me.dionclei.workshopspringboot.resources;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.workshopspringboot.entities.Order;
import me.dionclei.workshopspringboot.entities.dto.OrderDTO;
import me.dionclei.workshopspringboot.services.OrderService;
import me.dionclei.workshopspringboot.services.UserService;
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	
	private OrderService service;
	private UserService userService;
	
	public OrderResource(OrderService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll() {
		List<Order> order = service.findAll();
		List<OrderDTO> orderDTO = order.stream().map(Order::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(orderDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable long id, Principal principal) {
		var order = service.findById(id).toDTO();
		var user = userService.findByEmail(principal.getName());
		if(user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) &&
				order.getClient().getEmail() != user.getEmail()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok().body(order);
	}
	
}
