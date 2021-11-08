package com.tesodev.order.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.entity.Product;
import com.tesodev.order.exception.ServiceException;
import com.tesodev.order.mapper.ProductMapper;
import com.tesodev.order.repository.ProductRepository;
import com.tesodev.order.service.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

	private final Logger log = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	
	@Override
	public UUID create(ProductDTO productDTO) throws ServiceException {
		log.debug("Request to save Product : {}", productDTO);
		
		if(productDTO.getId() != null)
			throw new ServiceException("E01", "Product Id should be empty");
		
		Product product = productMapper.toEntity(productDTO);
		product = productRepository.save(product);
		return product.getId();
	}
	
	@Override
	public boolean delete(UUID productId) throws ServiceException {
		log.debug("Request to delete Product Id : {}", productId);
		
		Optional<Product> product = productRepository.findById(productId);
		if(!product.isPresent())
			throw new ServiceException("E03", "Product doesn't exist");
		
		product.get().setClosed(true);
		productRepository.save(product.get());
		
		return true;
	}

	@Override
	public boolean validate(UUID productId) {
		log.debug("Request to validate Product Id : {}", productId);
		return productRepository.existsById(productId);
	}

}
