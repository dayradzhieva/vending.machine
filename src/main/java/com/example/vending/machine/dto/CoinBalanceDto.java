package com.example.vending.machine.dto;

import com.example.vending.machine.entity.Coin;

import lombok.Data;

@Data
public class CoinBalanceDto {
	
	private Coin coinType;
	
	private int quantity;
}
