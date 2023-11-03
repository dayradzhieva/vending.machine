package com.example.vending.machine.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.example.vending.machine.entity.Coin;
import com.example.vending.machine.service.CoinBalanceService;

@RunWith(MockitoJUnitRunner.class)
public class CoinBalanceControllerTest {
	
	@InjectMocks
	private CoinBalanceController controller;
	
	@Mock
	private CoinBalanceService coinBalanceService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
		.build();
	}
	
	@Test
	public void testInsertCoin() throws Exception {
		
		when(this.coinBalanceService.getBalance()).thenReturn(0.5);
		
		this.mockMvc.perform(put("/vending-machine/coins/" + Coin.FIFTY_COINS))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string("0.5"));
	}
	
	@Test
	public void testReturnInsertedCoins() throws Exception {
		
		this.mockMvc.perform(delete("/vending-machine/coins/"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json("[]"));
	}
	
}
