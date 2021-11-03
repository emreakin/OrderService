package com.tesodev.order.service;

import java.util.List;
import java.util.UUID;

import com.tesodev.order.dto.OrderDTO;

public interface IOrderService {

	UUID create(OrderDTO orderDTO);
	
	boolean update(OrderDTO orderDTO);
	
	boolean delete(UUID orderId);
	
	List<OrderDTO> getAll();
	
	OrderDTO get(UUID orderId);
	
	boolean changeStatus(UUID orderId, String status);
}
