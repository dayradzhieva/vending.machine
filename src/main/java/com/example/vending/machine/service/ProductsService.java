package com.example.vending.machine.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.entity.ProductEntity;
import com.example.vending.machine.repository.ProductDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductsService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	private final ProductDao productDao;
	
	
	@Transactional
	public ProductDto createProduct(ProductDto dto){
		Optional<ProductEntity> productOptional = productDao.findById(dto.getName());
		if (productOptional.isPresent()) {
			throw new IllegalArgumentException(String.format("Product with name [%s] is already created", dto.getName()));
		}
		validateDto(dto);
		ProductEntity productEntity = modelMapper.map(dto, ProductEntity.class);
		
		return modelMapper.map(productDao.save(productEntity), ProductDto.class);
	}
	
	@Transactional
	public List<ProductDto> listAllProducts() {
		List<ProductEntity> productEntities = productDao.findAll();
				
		return productEntities.stream()
			.filter(productEntity -> productEntity.getQuantity() > 0)
			.map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
			.collect(Collectors.toList());
	}
	
	@Transactional
	public ProductDto updateProduct(final String productName, final ProductDto dto) {
		Optional<ProductEntity> productOptional = productDao.findById(productName);
		if (!productOptional.isPresent()) {
			throw new IllegalArgumentException(String.format("Product with name [%s] is not found", productName));
		}
		validateDto(dto);
		
		ProductEntity productEntity = productOptional.get();
		productEntity.setName(dto.getName());
		productEntity.setPrice(dto.getPrice());
		productEntity.setQuantity(dto.getQuantity());
		
		return modelMapper.map(productDao.save(productEntity), ProductDto.class);
	}
	
	@Transactional
	public void deleteProduct(final String productName) {
		productDao.deleteById(productName);
	}
	
	private void validateDto(final ProductDto dto) {
		if (dto.getQuantity() > 10) {
			throw new IllegalArgumentException(String.format("The machine can hold up to 10 pieces of [%s]", dto.getName()));
		}
		if (dto.getPrice() <= 0) {
			throw new IllegalArgumentException(String.format("The price of product [%s] should be positive number", dto.getName()));
		}
	}
}
