package com.tesodev.order.mapper;

import org.mapstruct.*;

import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.entity.Product;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
	
	@Named("id")
	ProductDTO toDtoId(Product product);
}
