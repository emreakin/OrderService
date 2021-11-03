package com.tesodev.order.service;

import java.util.UUID;

import com.tesodev.order.dto.ProductDTO;

public interface IProductService {

	UUID create(ProductDTO productDTO);
	
	boolean delete(UUID productId);
	
	boolean validate(UUID productId);
}
