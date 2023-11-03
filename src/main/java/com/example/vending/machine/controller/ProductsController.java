package com.example.vending.machine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.service.ProductsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Products", description = "Products APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping(ProductsController.MAPPING)
public class ProductsController {
	
	static final String MAPPING = "/vending-machine/products";
	
	private final ProductsService service;
	@Operation(summary = "Create a product")
	@PostMapping
	public ProductDto createProduct(@RequestBody ProductDto dto) {
		
		return this.service.createProduct(dto);
	}
	
	@Operation(summary = "List all products")
	@GetMapping("/list")
	public List<ProductDto> list() {
		
		return this.service.listAllProducts();
	}
	
	@Operation(summary = "Update a product")
	@PutMapping("/{productName}")
	public ProductDto updateProduct(@PathVariable(name = "productName") String productName, @RequestBody ProductDto dto) {
		
		return this.service.updateProduct(productName, dto);
	}
	
	@Operation(summary = "Delete a product")
	@DeleteMapping("/{productName}")
	public void deleteProduct(@PathVariable(name = "productName") String productName){
		this.service.deleteProduct(productName);
	}
	
}
