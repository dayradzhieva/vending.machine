package com.example.vending.machine.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.entity.ProductEntity;
import com.example.vending.machine.repository.CoinBalanceDao;
import com.example.vending.machine.repository.ProductDao;
/**
 * Unit tests for {@link VendingMachineService}.
 *
 * @author td.
 * @version 0.1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class VendingMachineServiceTest {
	
	@InjectMocks
	private VendingMachineService service;
	
	@Mock
	private CoinBalanceService coinBalanceService;
	
	@Mock
	private ProductDao productDao;
	
	@Mock
	private CoinBalanceDao coinBalanceDao;
	
	@Test
	public void testBuyProduct(){
		ProductEntity productEntity = new ProductEntity();
		productEntity.setQuantity(1);
		productEntity.setName("Chips");
		productEntity.setPrice(1.2);
		double balance = 1.5;
		
		when(this.productDao.findById(productEntity.getName())).thenReturn(Optional.of(productEntity));
		when(coinBalanceService.getBalance()).thenReturn(balance);
		
		ProductDto product = this.service.buyProduct(productEntity.getName());
		
		Assert.assertEquals(productEntity.getName(), product.getName());
	}
	
	@Test
	public void testBuyProductNotEnoughBalance(){
		ProductEntity productEntity = new ProductEntity();
		productEntity.setQuantity(5);
		productEntity.setName("Chips");
		productEntity.setPrice(1.2);
		double balance = 0.5;
		
		when(this.productDao.findById(productEntity.getName())).thenReturn(Optional.of(productEntity));
		when(coinBalanceService.getBalance()).thenReturn(balance);
		
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> this.service.buyProduct(productEntity.getName()))
		.withMessage(String.format("Your balance [%s] is insufficient for %s product with a price %s", balance, productEntity.getName(),
		productEntity.getPrice()));
	}
	
	@Test
	public void testBuyProductWithNotPresentProduct(){
		ProductEntity productEntity = new ProductEntity();
		productEntity.setQuantity(5);
		productEntity.setName("Chips");
		productEntity.setPrice(1.2);
		
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> this.service.buyProduct(productEntity.getName()))
		.withMessage(String.format("Product with name [%s] is not present", productEntity.getName()));
	}
	
	@Test
	public void testBuyProductNotEnoughQuantity(){
		ProductEntity productEntity = new ProductEntity();
		productEntity.setQuantity(0);
		productEntity.setName("Chips");
		productEntity.setPrice(1.2);
		
		when(this.productDao.findById(productEntity.getName())).thenReturn(Optional.of(productEntity));
		
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> this.service.buyProduct(productEntity.getName()))
		.withMessage(String.format("There isn`t enough quantity of product [%s]", productEntity.getName()));
	}
}
