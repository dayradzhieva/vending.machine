package com.example.vending.machine.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.entity.ProductEntity;
import com.example.vending.machine.repository.ProductDao;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductServiceTest {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	@InjectMocks
	private ProductsService service;
	
	@Mock
	private ProductDao productDao;
	
	@Test
	public void testCreateProduct() {
		ProductDto product = new ProductDto();
		product.setName("Chips");
		product.setPrice(1.2);
		product.setQuantity(5);
		
		when(this.productDao.save(modelMapper.map(product, ProductEntity.class)))
		.thenReturn(modelMapper.map(product, ProductEntity.class));
		
		ProductDto createdProduct = this.service.createProduct(product);
		
		Assert.assertEquals(createdProduct.getName(), product.getName());
		Assert.assertEquals(createdProduct.getPrice(), product.getPrice(), 0);
		Assert.assertEquals(createdProduct.getQuantity(), product.getQuantity());
	}
	
	@Test
	public void testListAllProducts() {
		ProductDto product = new ProductDto();
		product.setName("Chips");
		product.setPrice(1.2);
		product.setQuantity(5);
		
		when(this.productDao.findAll())
		.thenReturn(Collections.singletonList(modelMapper.map(product, ProductEntity.class)));
		
		List<ProductDto> products = this.service.listAllProducts();
		
		Assert.assertEquals(products.get(0).getName(), product.getName());
		Assert.assertEquals(products.get(0).getQuantity(), product.getQuantity());
		Assert.assertEquals(products.get(0).getPrice(), product.getPrice(), 0);
	}
	
	@Test
	public void testUpdateProduct() {
		ProductDto product = new ProductDto();
		product.setName("Chips");
		product.setPrice(1.2);
		product.setQuantity(5);
	
		when(this.productDao.findById(product.getName()))
		.thenReturn((Optional.of(modelMapper.map(product, ProductEntity.class))));
		
		product.setPrice(1.5);
		
		when(this.productDao.save(modelMapper.map(product, ProductEntity.class)))
		.thenReturn(modelMapper.map(product, ProductEntity.class));
		
		this.service.updateProduct(product.getName(), product);
		
		Assert.assertEquals(1.5, product.getPrice(), 0);
	}
	
	@Test
	public void testDeleteProduct() {
		this.service.deleteProduct("Chips");
	}
	
	@Test
	public void testCreateProductWithNegativePrice() {
		ProductDto product = new ProductDto();
		product.setName("Chips");
		product.setPrice(-1.2);
		product.setQuantity(5);
		
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> this.service.createProduct(product))
		.withMessage(String.format("The price of product [%s] should be positive number", product.getName()));
	}
	
	@Test
	public void testUpdateProductThatNotExists() {
		ProductDto product = new ProductDto();
		product.setName("Chips");
		product.setPrice(1.2);
		product.setQuantity(5);
		
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> this.service.updateProduct(product.getName(), product))
		.withMessage(String.format("Product with name [%s] is not found", product.getName()));
	}
	
}
