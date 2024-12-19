package me.dionclei.workshopspringboot.resources;

import java.lang.System.Logger;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import me.dionclei.workshopspringboot.entities.Order;
import me.dionclei.workshopspringboot.entities.OrderItem;
import me.dionclei.workshopspringboot.entities.Product;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.entities.dto.OrderDTO;
import me.dionclei.workshopspringboot.entities.dto.OrderItemRequest;
import me.dionclei.workshopspringboot.entities.dto.OrderRequest;
import me.dionclei.workshopspringboot.entities.dto.UserDTO;
import me.dionclei.workshopspringboot.enums.OrderStatus;
import me.dionclei.workshopspringboot.services.OrderService;
import me.dionclei.workshopspringboot.services.ProductService;
import me.dionclei.workshopspringboot.services.UserService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
	    List<User> users = userService.findAll();
	    List<UserDTO> usersDTO = users.stream()
	                                  .map(User::toDTO)
	                                  .collect(Collectors.toList());
	    return ResponseEntity.ok(usersDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable long id) {
		return ResponseEntity.ok().body(userService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody User obj) {
		obj = userService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj.toDTO());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User obj) {
		obj = userService.update(id, obj);
		return ResponseEntity.ok().body(obj.toDTO());
	}
	
	@PutMapping(value = "/{id}/orders")
	public ResponseEntity<OrderDTO> addOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
		
		User user = userService.findById(id);
		Order order = new Order();
		order.setClient(user);
		order.setMoment(Instant.now());
		order.setId(null);
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		for(OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
			Product prod = productService.findById(orderItemRequest.getProductId());
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(prod);
			orderItem.setQuantity(orderItemRequest.getQuantity());
			orderItem.setPrice(prod.getPrice());
			orderItem.setOrder(order);
			order.getItems().add(orderItem);
		}
		user.getOrders().add(order);
		orderService.save(order);
		userService.insert(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(order.toDTO());
	}
	
}
