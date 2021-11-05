package com.tesodev.order.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tesodev.order.dto.OrderDTO;
import com.tesodev.order.entity.Order;
import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.mapper.OrderMapper;
import com.tesodev.order.repository.OrderRepository;
import com.tesodev.order.service.IAddressService;
import com.tesodev.order.service.IOrderService;
import com.tesodev.order.service.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
	
	private final Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	
	private final IAddressService addressService;
	private final IProductService productService;

	@Override
	public UUID create(OrderDTO orderDTO) {
		log.debug("Request to save Order : {}", orderDTO);
		
		AddressDTO address = orderDTO.getAddress();
		if(address.getId() == null || !addressService.validate(address.getId())) {
			address.setId(null);
			UUID newAddressId = addressService.create(address);
			orderDTO.getAddress().setId(newAddressId);
		}
		
		ProductDTO product = orderDTO.getProduct();
		if(product.getId() == null || !productService.validate(product.getId())) {
			product.setId(null);
			UUID newProductId = productService.create(product);
			orderDTO.getProduct().setId(newProductId);
		}
		
		Order order = orderMapper.toEntity(orderDTO);
		order = orderRepository.save(order);
		return order.getId();
	}

	@Override
	public boolean update(OrderDTO orderDTO) {
		log.debug("Request to update Order : {}", orderDTO);
		
		if(!orderRepository.existsById(orderDTO.getId()))
			return false;
		
		orderRepository.save(orderMapper.toEntity(orderDTO));
		
		return true;
	}

	@Override
	public boolean delete(UUID orderId) {
		log.debug("Request to delete Order Id : {}", orderId);
		
		if(!orderRepository.existsById(orderId))
			return false;
		
		Optional<Order> order = orderRepository.findById(orderId);
		if(!order.isPresent())
			return false;
		
		if(!addressService.delete(order.get().getAddress().getId()))
			return false;
		
		if(!productService.delete(order.get().getProduct().getId()))
			return false;
		
		order.get().setClosed(true);
		orderRepository.save(order.get());
		
		return true;
	}

	@Override
	public List<OrderDTO> getAll() {
		log.debug("Request to get all Orders");
		return orderRepository.findAll().stream().map(orderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}

	@Override
	public OrderDTO get(UUID orderId) {
		log.debug("Request to get Order Id : {}", orderId);
		Optional<OrderDTO> orderDTO = orderRepository.findById(orderId).map(orderMapper::toDto);
		
		if(orderDTO.isPresent())
			return orderDTO.get();
		
		return null;
	}

	@Override
	public boolean changeStatus(UUID orderId, String status) {
		log.debug("Request to changeStatus Order Id : {}", orderId);
		Optional<Order> order = orderRepository.findById(orderId);
		
		if(!order.isPresent())
			return false;
		
		order.get().setStatus(status);
		orderRepository.save(order.get());
		
		return true;
	}

}
