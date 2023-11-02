package com.example.vending.machine.entity;

public enum Coin {
	TEN_COINS("10st",0.1),
	TWENTY_COINS("20st", 0.2),
	FIFTY_COINS("50st", 0.5),
	ONE_LEV("1lv", 1),
	TWO_LEVS("2lv", 2);
	
	private final String name;
	private final double value;
	
	private Coin(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	public static Coin getCoin(String name) {
		for (Coin coin: values()) {
			if (coin.getName().equals(name)) {
				return coin;
			}
		}
		throw new IllegalArgumentException(String.format("Coin with name [%s] is not supported", name));
	}
	
	public String getName() {
		return name;
	}
	
	public double getValue(){
		return this.value;
	}
}
