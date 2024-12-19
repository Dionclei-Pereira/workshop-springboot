package me.dionclei.workshopspringboot.entities.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
	
	private List<OrderItemRequest> orderItems = new ArrayList<>();

	public List<OrderItemRequest> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemRequest> orderitems) {
		this.orderItems = orderitems;
	}
	
}
