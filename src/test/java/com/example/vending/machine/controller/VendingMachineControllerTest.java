package com.example.vending.machine.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.service.CoinBalanceService;
import com.example.vending.machine.service.VendingMachineService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class VendingMachineControllerTest {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@InjectMocks
	private VendingMachineController controller;
	
	@Mock
	private VendingMachineService vendingMachineService;
	
	@Mock
	private CoinBalanceService coinBalanceService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
		.build();
	}
	
	@Test
	public void testBuyProduct() throws Exception {
		ProductDto product = new ProductDto();
		product.setName("Chips");
		product.setQuantity(5);
		product.setPrice(1.2);
		when(this.vendingMachineService.buyProduct(product.getName())).thenReturn(product);
		
		this.mockMvc.perform(post("/vending-machine/buy/" + product.getName()))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(MAPPER.writeValueAsString(product)));
	}

}
