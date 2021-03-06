package com.tesodev.order.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.entity.Product;
import com.tesodev.order.exception.ErrorCodes;
import com.tesodev.order.exception.ServiceException;
import com.tesodev.order.mapper.ProductMapper;
import com.tesodev.order.repository.ProductRepository;
import com.tesodev.order.service.impl.ProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceTest {

	@Mock
    private ProductRepository productRepository;
	
	@Mock
    private ProductMapper productMapper;
	
	@InjectMocks
    private ProductService productService;
	
	private final UUID GENERATED_PRODUCT_ID = UUID.fromString("3c1b17db-d4bd-46e9-b582-86bfdbdc0723");
	
	@Test
    public void whenCreateProductShouldReturnProductId() {		
		Product product = createDummyProduct(GENERATED_PRODUCT_ID);
		
		when(productMapper.toEntity(ArgumentMatchers.any(ProductDTO.class))).thenReturn(product);		
		when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
		
		ProductDTO productDTO = createDummyProductDTO(null);
		UUID productId = null;
		try {
			productId = productService.create(productDTO);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
		
		assertNotNull(productId);
    }
	
	@Test
    public void whenCreateProductShouldThrownExceptionIfProductIdNotEmpty() {		
		
		ProductDTO productDTO = createDummyProductDTO(GENERATED_PRODUCT_ID);
		try {
			productService.create(productDTO);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), ErrorCodes.ID_SHOULD_EMPTY);
		}
    }
	
	@Test
	public void whenShouldDeleteProductByGivenProductIdIfFound() {
		Product product = createDummyProduct(GENERATED_PRODUCT_ID);
		
		when(productRepository.findById(GENERATED_PRODUCT_ID)).thenReturn(Optional.of(product));
		
		boolean deleteStatus = false;
		try {
			deleteStatus = productService.delete(GENERATED_PRODUCT_ID);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
		
		assertTrue(deleteStatus);
	}
	
	@Test
	public void whenGivenProductIdShouldValidateProduct() {
		when(productRepository.existsById(GENERATED_PRODUCT_ID)).thenReturn(true);
		
		assertTrue(productService.validate(GENERATED_PRODUCT_ID));
	}
	
	private ProductDTO createDummyProductDTO(UUID uuid) {
		return new ProductDTO(uuid, "http://product.images.com/ghsagd8deevdv", "First Image");
	}
	
	private Product createDummyProduct(UUID uuid) {
		return new Product(uuid, "http://product.images.com/ghsagd8deevdv", "First Image", false);
	}
}
