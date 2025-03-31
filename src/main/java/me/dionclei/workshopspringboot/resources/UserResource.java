package me.dionclei.workshopspringboot.resources;

import java.net.URI;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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
	
	private UserService userService;
	private ProductService productService;
	private OrderService orderService;
	
	public UserResource(UserService userService, ProductService productService, OrderService orderService) {
		this.userService = userService;
		this.productService = productService;
		this.orderService = orderService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
	    List<User> users = userService.findAll();
	    List<UserDTO> usersDTO = users.stream()
	                                  .map(User::toDTO)
	                                  .collect(Collectors.toList());
	    return ResponseEntity.ok(usersDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable long id, Principal principal) {
		var user = userService.findByEmail(principal.getName());
		if(user.getId() != id && user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) { 
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	    return ResponseEntity.ok().body(userService.findById(id).toDTO());
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody User obj) {
		obj = userService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj.toDTO());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
		var user = userService.findByEmail(principal.getName());
		if (id != user.getId() && user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User obj, Principal principal) {
		var user = userService.findByEmail(principal.getName());
		if (id != user.getId() && user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		obj = userService.update(id, obj);
		return ResponseEntity.ok().body(obj.toDTO());
	}
	
	@GetMapping(value = "/{id}/orders")
	public ResponseEntity<List<OrderDTO>> getOrders(@PathVariable Long id, Principal principal) {
		var user = userService.findByEmail(principal.getName());
		if (id != user.getId() && user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(userService.getOrders(id)) ;
	}
	
	@PutMapping(value = "/{id}/orders")
	public ResponseEntity<OrderDTO> addOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest, Principal principal) {
		var user = userService.findByEmail(principal.getName());
		if (id != user.getId() && user.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		user = userService.findById(id);
		Order order = new Order();
		order.setClient(user);
		order.setMoment(Instant.now());
		order.setId(null);
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		for(OrderItemRequest orderItemRequest : orderRequest.orderItems()) {
			Product prod = productService.findById(orderItemRequest.productId());
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(prod);
			orderItem.setQuantity(orderItemRequest.quantity());
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
