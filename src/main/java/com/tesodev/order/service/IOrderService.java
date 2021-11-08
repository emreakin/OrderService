package com.tesodev.order.service;

import java.util.List;
import java.util.UUID;

import com.tesodev.order.dto.OrderDTO;
import com.tesodev.order.exception.ServiceException;

public interface IOrderService {

	UUID create(OrderDTO orderDTO) throws ServiceException;
	
	boolean update(OrderDTO orderDTO) throws ServiceException;
	
	boolean delete(UUID orderId) throws ServiceException;
	
	List<OrderDTO> getAll();
	
	OrderDTO get(UUID orderId);
	
	boolean changeStatus(UUID orderId, String status) throws ServiceException;
}
