package me.dionclei.workshopspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.workshopspringboot.entities.Category;
import me.dionclei.workshopspringboot.repositories.CategoryRepository;
import me.dionclei.workshopspringboot.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Category findById(Long id) {
		Optional<Category> cat = categoryRepository.findById(id);
		if (cat.isPresent()) {
			return cat.get();
		}
		throw new ResourceNotFoundException(id);
	}
}
