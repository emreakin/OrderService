package com.tesodev.order.service;

import java.util.UUID;

import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.exception.ServiceException;

public interface IAddressService {

	UUID create(AddressDTO addressDTO) throws ServiceException;
	
	boolean delete(UUID addressId) throws ServiceException;
	
	boolean validate(UUID addressId);
}
