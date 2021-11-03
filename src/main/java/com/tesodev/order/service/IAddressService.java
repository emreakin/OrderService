package com.tesodev.order.service;

import java.util.UUID;

import com.tesodev.order.dto.AddressDTO;

public interface IAddressService {

	UUID create(AddressDTO addressDTO);
	
	boolean delete(UUID addressId);
	
	boolean validate(UUID addressId);
}
