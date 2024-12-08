package me.dionclei.workshopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.workshopspringboot.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
