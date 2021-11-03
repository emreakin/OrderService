package com.tesodev.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tesodev.order.dto.OrderDTO;
import com.tesodev.order.entity.Order;


@Mapper(componentModel = "spring", uses = { AddressMapper.class, ProductMapper.class })
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
	
	@Mapping(target = "address", source = "address", qualifiedByName = "id")
	@Mapping(target = "product", source = "product", qualifiedByName = "id")
	OrderDTO toDto(Order s);
}
