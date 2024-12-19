package me.dionclei.workshopspringboot.entities.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import me.dionclei.workshopspringboot.entities.OrderItem;
import me.dionclei.workshopspringboot.entities.Payment;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.enums.OrderStatus;

public class OrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant moment;
	private UserDTO client;
	private Integer orderStatus;
	private Set<OrderItem> items = new HashSet<>();
	private Payment payment;
	
	public OrderDTO() {}
	
	public OrderDTO(Long id, Instant moment,OrderStatus orderStatus ,User client) {
		super();
		this.client = client.toDTO();
		this.moment = moment;
		setOrderStatus(orderStatus);
		this.id = id;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Set<OrderItem> getItems() {
		return items;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}
	
	public OrderStatus getOrderStatus() {
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		if (orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}

	public UserDTO getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client.toDTO();
	}

	public Double getTotal() {
		double sum = 0;
		for (OrderItem x : items) {
			sum += x.getSubTotal();
		}
		return sum;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(client, id, moment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDTO other = (OrderDTO) obj;
		return Objects.equals(client, other.client) && Objects.equals(id, other.id)
				&& Objects.equals(moment, other.moment);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", moment=" + moment + ", client=" + client + "]";
	}
	
	
	
}
