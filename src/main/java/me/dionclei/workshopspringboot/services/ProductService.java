package me.dionclei.workshopspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.workshopspringboot.entities.Product;
import me.dionclei.workshopspringboot.repositories.ProductRepository;
import me.dionclei.workshopspringboot.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	ProductRepository repository;
	
	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)
	public Product findById(long id) {
		Optional<Product> obj = repository.findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		throw new ResourceNotFoundException(id);
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public Product save(Product product) {
		return repository.save(product);
	}
	
}
