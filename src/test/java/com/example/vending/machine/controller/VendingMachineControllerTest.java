package com.example.vending.machine.controller;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.vending.machine.dto.ProductDto;
import com.example.vending.machine.service.VendingMachineService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link VendingMachineController}.
 *
 * @author td.
 * @version 0.1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class VendingMachineControllerTest {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@InjectMocks
	private VendingMachineController controller;
	
	@Mock
	private VendingMachineService vendingMachineService;
	
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
		
		this.mockMvc.perform(post(VendingMachineController.MAPPING + "/products/" + product.getName() + "/buy"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(MAPPER.writeValueAsString(product)));
		
		verify(this.vendingMachineService).buyProduct(product.getName());
		
	}

}
