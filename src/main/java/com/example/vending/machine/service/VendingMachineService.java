package com.example.vending.machine.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.entity.ProductEntity;
import com.example.vending.machine.repository.CoinBalanceDao;
import com.example.vending.machine.repository.ProductDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VendingMachineService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	private final CoinBalanceService coinBalanceService;
	private final CoinBalanceDao coinBalanceDao;
	private final ProductDao productDao;
	
	@Transactional
	public ProductDto buyProduct(final String productName) {
		Optional<ProductEntity> optionalProduct = productDao.findById(productName);
		if (!optionalProduct.isPresent()) {
			throw new IllegalArgumentException(String.format("Product with name [%s] is not present", productName));
		}
		
		ProductEntity product = optionalProduct.get();
		if (product.getQuantity() <= 0){
			throw new IllegalArgumentException(String.format("There isn`t enough quantity of product [%s]", productName));
		}
		
		double balance = coinBalanceService.getBalance();
		if (balance - product.getPrice() >= 0) {
			// TODO: return change
			coinBalanceDao.deleteAll();
			product.setQuantity(product.getQuantity() - 1);
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			if(product.getQuantity() == 0){
				productDao.delete(product);
			}
			return productDto;
		} else {
			throw new IllegalArgumentException(
				String.format("Your balance [%s] is insufficient for %s product with a price %s", balance, product.getName(),
				product.getPrice())
			);
		}
	}
	
	
}
