package me.dionclei.workshopspringboot.entities.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import me.dionclei.workshopspringboot.entities.Order;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String email;
	
	@JsonIgnore
	private List<Order> orders = new ArrayList<>();
	
	public UserDTO() {}

	public UserDTO(Long id, String name, String email, List<Order> orders) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.orders = orders;
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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	
	
}
