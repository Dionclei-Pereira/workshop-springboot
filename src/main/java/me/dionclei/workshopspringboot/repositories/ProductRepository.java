package me.dionclei.workshopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.workshopspringboot.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
