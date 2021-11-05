package com.tesodev.order.model;

import java.io.Serializable;
import java.util.List;

import com.tesodev.order.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersResultModel implements Serializable {

	private static final long serialVersionUID = 5222347255293885347L;

	private List<OrderDTO> orders;
}
