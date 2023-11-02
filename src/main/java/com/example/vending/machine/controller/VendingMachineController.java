package com.example.vending.machine.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.service.VendingMachineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Tag(name = "Vending Machine", description = "Vending Machine APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping(VendingMachineController.MAPPING)
public class VendingMachineController {
	public static final String MAPPING = "/vending-machine";
	
	private final VendingMachineService service;
	@Operation(summary = "Buy a product")
	@PostMapping (value = "/products/{productName}/buy")
	ProductDto buyProduct(@PathVariable String productName) {
		
		return this.service.buyProduct(productName);
	}
	
}