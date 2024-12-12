package me.dionclei.workshopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.workshopspringboot.entities.OrderItem;
import me.dionclei.workshopspringboot.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
