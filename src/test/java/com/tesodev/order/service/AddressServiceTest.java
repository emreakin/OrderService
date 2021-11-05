package com.tesodev.order.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.entity.Address;
import com.tesodev.order.mapper.AddressMapper;
import com.tesodev.order.repository.AddressRepository;
import com.tesodev.order.service.impl.AddressService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddressServiceTest {

	@Mock
    private AddressRepository addressRepository;
	
	@Mock
    private AddressMapper addressMapper;
	
	@InjectMocks
    private AddressService addressService;
	
	private final UUID GENERATED_ADDRESS_ID = UUID.fromString("3c1b17db-d4bd-46e9-b582-86bfdbdc0723");
	
	@Test
    public void whenCreateAdressShouldReturnAddressId() {		
		Address address = createDummyAddress(GENERATED_ADDRESS_ID);
		
		when(addressMapper.toEntity(ArgumentMatchers.any(AddressDTO.class))).thenReturn(address);		
		when(addressRepository.save(ArgumentMatchers.any(Address.class))).thenReturn(address);
		
		AddressDTO addressDTO = createDummyAddressDTO(null);
		UUID addressId = addressService.create(addressDTO);
		
		assertNotNull(addressId);
    }
	
	@Test
	public void whenShouldDeleteAddressByGivenAddressIdIfFound() {
		Address address = createDummyAddress(GENERATED_ADDRESS_ID);
		
		when(addressRepository.existsById(GENERATED_ADDRESS_ID)).thenReturn(true);
		when(addressRepository.findById(GENERATED_ADDRESS_ID)).thenReturn(Optional.of(address));
		
		assertTrue(addressService.delete(GENERATED_ADDRESS_ID));
	}
	
	@Test
	public void whenGivenAddressIdShouldValidateAddress() {
		when(addressRepository.existsById(GENERATED_ADDRESS_ID)).thenReturn(true);
		
		assertTrue(addressService.validate(GENERATED_ADDRESS_ID));
	}
	
	private AddressDTO createDummyAddressDTO(UUID uuid) {
		return new AddressDTO(uuid, "Test Address", "Antalya", "Turkey", 7);
	}
	
	private Address createDummyAddress(UUID uuid) {
		return new Address(uuid, "Test Address", "Antalya", "Turkey", 7, false);
	}
}