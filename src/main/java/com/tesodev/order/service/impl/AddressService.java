package com.tesodev.order.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.entity.Address;
import com.tesodev.order.exception.ServiceException;
import com.tesodev.order.mapper.AddressMapper;
import com.tesodev.order.repository.AddressRepository;
import com.tesodev.order.service.IAddressService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {

	private final Logger log = LoggerFactory.getLogger(AddressService.class);

	private final AddressRepository addressRepository;
	private final AddressMapper addressMapper;
	
	@Override
	public UUID create(AddressDTO addressDTO) throws ServiceException {
		log.debug("Request to save Address : {}", addressDTO);
		
		if(addressDTO.getId() != null)
			throw new ServiceException("E01", "Address Id should be empty");
		
		Address address = addressMapper.toEntity(addressDTO);
		address = addressRepository.save(address);
		return address.getId();
	}
	
	@Override
	public boolean delete(UUID addressId) throws ServiceException {
		log.debug("Request to delete Address Id : {}", addressId);
		
		Optional<Address> address = addressRepository.findById(addressId);
		if(!address.isPresent())
			throw new ServiceException("E03", "Address doesn't exist");
		
		address.get().setClosed(true);
		addressRepository.save(address.get());
		
		return true;
	}

	@Override
	public boolean validate(UUID addressId) {
		log.debug("Request to validate Address Id : {}", addressId);
		return addressRepository.existsById(addressId);
	}

}
