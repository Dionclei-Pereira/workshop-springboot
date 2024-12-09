package me.dionclei.workshopspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.workshopspringboot.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
