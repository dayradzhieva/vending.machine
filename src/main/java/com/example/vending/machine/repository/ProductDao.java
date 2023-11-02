package com.example.vending.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.vending.machine.entity.ProductEntity;
@Repository
public interface ProductDao extends JpaRepository<ProductEntity, String> {


}
