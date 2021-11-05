package com.tesodev.order.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 8430452469267447141L;

	private UUID id;
    private UUID customerId;
    private int quantity;
    private double price;
    private String status;
    private AddressDTO address;
    private ProductDTO product;
}
