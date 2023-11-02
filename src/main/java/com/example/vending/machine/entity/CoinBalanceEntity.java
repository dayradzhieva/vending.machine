package com.example.vending.machine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "coin_balance")
public class CoinBalanceEntity {
	@Id
	@Column(name = "coin_type")
	@Enumerated(EnumType.STRING)
	private Coin coinType;
	
	@Column(name = "quantity")
	private int quantity;
}
