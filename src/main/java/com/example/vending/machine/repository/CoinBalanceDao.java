package com.example.vending.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vending.machine.entity.Coin;
import com.example.vending.machine.entity.CoinBalanceEntity;
@Repository
public interface CoinBalanceDao extends JpaRepository<CoinBalanceEntity, Coin> {

}
