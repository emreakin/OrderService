package com.tesodev.order.mapper;

import org.mapstruct.*;

import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.entity.Address;

@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
	
	@Named("id")
	AddressDTO toDtoId(Address address);
}
