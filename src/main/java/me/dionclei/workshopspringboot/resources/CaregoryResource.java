package me.dionclei.workshopspringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.workshopspringboot.entities.Category;
import me.dionclei.workshopspringboot.entities.User;
import me.dionclei.workshopspringboot.services.CategoryService;
import me.dionclei.workshopspringboot.services.UserService;

@RestController
@RequestMapping(value = "/categories")
public class CaregoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}
	
}
