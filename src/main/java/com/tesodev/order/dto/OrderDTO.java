package com.tesodev.order.dto;

import java.io.Serializable;
import java.util.UUID;

import com.tesodev.order.entity.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 8430452469267447141L;

	private UUID id;
    private UUID customerId;
    private int quantity;
    private double price;
    private double status;
    private AddressDTO address;
    private Product product;
}
