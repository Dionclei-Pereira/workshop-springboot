package me.dionclei.workshopspringboot.entities.dto;

import java.util.List;

public record OrderRequest(List<OrderItemRequest> orderItems) {

}