package com.tesodev.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tesodev.order.entity.Address;

/**
 * Spring Data SQL repository for the Address entity.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
	
}
