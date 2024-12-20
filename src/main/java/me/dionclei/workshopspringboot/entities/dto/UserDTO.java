package me.dionclei.workshopspringboot.entities.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import me.dionclei.workshopspringboot.entities.Order;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String email;
	
	@JsonIgnore
	private List<OrderDTO> orders = new ArrayList<>();
	
	public UserDTO() {}

	public UserDTO(Long id, String name, String email, List<Order> orders) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.orders = orders.stream().map(Order::toDTO).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}
	
}
