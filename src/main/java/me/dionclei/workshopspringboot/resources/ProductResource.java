package me.dionclei.workshopspringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.workshopspringboot.entities.Product;
import me.dionclei.workshopspringboot.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	private ProductService service;
	
	public ProductResource(ProductService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value =  "/{id}")
	public ResponseEntity<Product> findById(@PathVariable long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
