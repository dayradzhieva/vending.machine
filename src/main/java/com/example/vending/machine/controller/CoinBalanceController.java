package com.example.vending.machine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vending.machine.dto.CoinBalanceDto;
import com.example.vending.machine.entity.Coin;
import com.example.vending.machine.service.CoinBalanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Coins", description = "Coins APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping( "/coins")
public class CoinBalanceController {
	
	private final CoinBalanceService service;
	
	@Operation(summary = "Insert a coin")
	@PutMapping ("/{coin}")
	public double insertCoin(@PathVariable(name = "coin") Coin coin) {
		service.insertCoin(coin);
		return service.getBalance();
	}
	
	@Operation(summary = "Return all inserted coins")
	@DeleteMapping("/")
	public List<CoinBalanceDto> returnInsertedCoin() {
		return service.returnInsertedCoin();
	}
	
}
