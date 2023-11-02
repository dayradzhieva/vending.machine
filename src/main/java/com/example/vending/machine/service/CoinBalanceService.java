package com.example.vending.machine.service;


import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.vending.machine.dto.CoinBalanceDto;
import com.example.vending.machine.entity.Coin;
import com.example.vending.machine.entity.CoinBalanceEntity;
import com.example.vending.machine.repository.CoinBalanceDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoinBalanceService {
	
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	private final CoinBalanceDao coinBalanceDao;
	
	@Transactional
	public void insertCoin(final Coin coinType) {
		Optional<CoinBalanceEntity> coinBalanceOptional = coinBalanceDao.findById(coinType);
		if (!coinBalanceOptional.isPresent()) {
			CoinBalanceEntity newCoin = new CoinBalanceEntity();
			newCoin.setCoinType(coinType);
			newCoin.setQuantity(1);
			coinBalanceDao.save(newCoin);
		} else {
			CoinBalanceEntity coinBalance = coinBalanceOptional.get();
			coinBalance.setQuantity(coinBalance.getQuantity() + 1);
			coinBalanceDao.save(coinBalance);
		}
	}
	
	@Transactional
	public List<CoinBalanceDto> returnInsertedCoin() {
		List<CoinBalanceEntity> coinBalance = coinBalanceDao.findAll();
		
		List<CoinBalanceDto> coins = coinBalance.stream()
			.map(c -> modelMapper.map(c, CoinBalanceDto.class))
			.collect(Collectors.toList());
		
		coinBalanceDao.deleteAll();
		return coins;
	}
	
	public double getBalance() {
		List<CoinBalanceEntity> coinBalanceEntities = coinBalanceDao.findAll();
		double balance = coinBalanceEntities.stream()
		.map(c -> c.getCoinType().getValue() * c.getQuantity())
		.mapToDouble(Double::doubleValue)
		.sum();
		return Double.valueOf(decimalFormat.format(balance));
	}
}
