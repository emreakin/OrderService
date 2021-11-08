package com.tesodev.order.service;

import java.util.UUID;

import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.exception.ServiceException;

public interface IProductService {

	UUID create(ProductDTO productDTO) throws ServiceException;
	
	boolean delete(UUID productId) throws ServiceException;
	
	boolean validate(UUID productId);
}
