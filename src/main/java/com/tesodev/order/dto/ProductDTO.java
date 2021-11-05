package com.tesodev.order.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = -5434506978038118941L;
	
	private UUID id;
    private String imageUrl;
    private String name;
    
}
