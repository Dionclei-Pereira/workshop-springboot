package me.dionclei.workshopspringboot.resources;

import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import me.dionclei.workshopspringboot.entities.Category;
import me.dionclei.workshopspringboot.entities.Product;
import me.dionclei.workshopspringboot.entities.dto.ProductRequest;
import me.dionclei.workshopspringboot.services.CategoryService;
import me.dionclei.workshopspringboot.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	private ProductService service;
	private CategoryService categoryService;
	
	public ProductResource(ProductService service, CategoryService categoryService) {
		this.service = service;
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Product> save(@RequestBody @Valid ProductRequest product) {
		Product p = new Product();
		p.setName(product.name());
		p.setDescription(product.description());
		p.setPrice(product.price());
		Set<Category> cat = new HashSet<>();
		for(Long id : product.categories()) {
			cat.add(categoryService.findById(id));
		}
		p.setCategories(cat);
		
		p = service.save(p);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
		
		return ResponseEntity.created(uri).body(p);
	}
	
	@GetMapping(value =  "/{id}")
	public ResponseEntity<Product> findById(@PathVariable long id) {
		Product obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
