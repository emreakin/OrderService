package com.tesodev.order.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tesodev.order.dto.OrderDTO;
import com.tesodev.order.model.BaseResponseModel;
import com.tesodev.order.model.OrdersResultModel;
import com.tesodev.order.model.IdResultModel;
import com.tesodev.order.model.StatusResultModel;
import com.tesodev.order.service.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private final IOrderService orderService;

	@PostMapping("/create")
	public BaseResponseModel<IdResultModel> createOrder(@RequestBody OrderDTO order) {
		BaseResponseModel<IdResultModel> response = new BaseResponseModel<>();
		
		UUID orderId = orderService.create(order);
		response.setResult(new IdResultModel(orderId));
		
		return response;
	}
	
	@PutMapping("/update")
	public BaseResponseModel<StatusResultModel> updateOrder(@RequestBody OrderDTO order) {
		BaseResponseModel<StatusResultModel> response = new BaseResponseModel<>();
		
		boolean resultStatus = orderService.update(order);
		response.setResult(new StatusResultModel(resultStatus));
		
		return response;
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseResponseModel<StatusResultModel> deleteOrder(@PathVariable(value = "id", required = true) UUID orderId) {
		BaseResponseModel<StatusResultModel> response = new BaseResponseModel<>();
		
		boolean resultStatus = orderService.delete(orderId);
		response.setResult(new StatusResultModel(resultStatus));
		
		return response;
	}
	
	@GetMapping("/list")
    public BaseResponseModel<OrdersResultModel> getAllOrders() {
		BaseResponseModel<OrdersResultModel> response = new BaseResponseModel<>();
		
		List<OrderDTO> orders = orderService.getAll();
		response.setResult(new OrdersResultModel(orders));
		
        return response;
    }
	
	@GetMapping("/get/{id}")
	public BaseResponseModel<OrderDTO> getOrder(@PathVariable(value = "id", required = true) UUID orderId) {
		BaseResponseModel<OrderDTO> response = new BaseResponseModel<>();
		
		OrderDTO order = orderService.get(orderId);
		response.setResult(order);
		
        return response;
    }
}
