package com.tesodev.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tesodev.order.entity.Product;

/**
 * Spring Data SQL repository for the Address entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
	
}
