package com.tesodev.order.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.dto.OrderDTO;
import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.entity.Address;
import com.tesodev.order.entity.Order;
import com.tesodev.order.entity.Product;
import com.tesodev.order.exception.ErrorCodes;
import com.tesodev.order.exception.ServiceException;
import com.tesodev.order.mapper.OrderMapper;
import com.tesodev.order.repository.OrderRepository;
import com.tesodev.order.service.impl.AddressService;
import com.tesodev.order.service.impl.OrderService;
import com.tesodev.order.service.impl.ProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceTest {

	@Mock
    private OrderRepository orderRepository;
	
	@Mock
    private OrderMapper orderMapper;
	
	@Mock
    private AddressService addressService;
	
	@Mock
    private ProductService productService;
	
	@InjectMocks
    private OrderService orderService;
	
	private final UUID GENERATED_ORDER_ID = UUID.fromString("bce7399d-57f6-4e44-8bd6-001d44518db2");
	private final UUID GENERATED_ADDRESS_ID = UUID.fromString("3c1b17db-d4bd-46e9-b582-86bfdbdc0723");
	private final UUID GENERATED_PRODUCT_ID = UUID.fromString("a451b4fa-36db-4591-8021-eab3be4754a7");
	private final UUID GENERATED_CUSTOMER_ID = UUID.fromString("32433151-9338-4249-889e-d1701f840c7d");
	
	@Test
    public void whenCreateOrderShouldReturnOrderId() {		
		Order order = createDummyOrder(GENERATED_ORDER_ID);
		
		when(orderMapper.toEntity(ArgumentMatchers.any(OrderDTO.class))).thenReturn(order);
		when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(order);
		
		OrderDTO orderDTO = createDummyOrderDTO(null);
		UUID orderId = null;
		try {
			orderId = orderService.create(orderDTO);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
		
		assertNotNull(orderId);
    }
	
	@Test
    public void whenCreateOrderShouldThrownExceptionIfOrderIdNotEmpty() {		
		
		OrderDTO orderDTO = createDummyOrderDTO(GENERATED_ORDER_ID);
		try {
			orderService.create(orderDTO);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), ErrorCodes.ID_SHOULD_EMPTY);
		}

    }
	
	@Test
    public void whenGivenOrderShouldUpdateOrderIfFound() {
		Order order = createDummyOrder(GENERATED_ORDER_ID);
        
        OrderDTO newOrder = createDummyOrderDTO(GENERATED_ORDER_ID);
        newOrder.setQuantity(5);
        
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        when(orderMapper.toEntity(ArgumentMatchers.any(OrderDTO.class))).thenReturn(order);

        boolean status = false;
		try {
			status = orderService.update(newOrder);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
        
        assertTrue(status);
    }
	
	@Test
    public void whenUpdateOrderShouldThrownExceptionIfOrderIdEmpty() {		
		
		OrderDTO orderDTO = createDummyOrderDTO(null);
		try {
			orderService.update(orderDTO);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), ErrorCodes.ID_SHOULD_NOT_EMPTY);
		}

    }
	
	@Test
	public void whenShouldDeleteOrderByGivenOrderIdIfFound() {
		Order order = createDummyOrder(GENERATED_ORDER_ID);
		order.getAddress().setId(GENERATED_ADDRESS_ID);
		order.getProduct().setId(GENERATED_PRODUCT_ID);

		boolean status = false;
		try {
			when(orderRepository.findById(GENERATED_ORDER_ID)).thenReturn(Optional.of(order));
			when(addressService.delete(GENERATED_ADDRESS_ID)).thenReturn(true);
			when(productService.delete(GENERATED_PRODUCT_ID)).thenReturn(true);
			
			status = orderService.delete(GENERATED_ORDER_ID);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
		
		assertTrue(status);
	}
	
	@Test
	public void shouldThrowExceptionWhenAdressNotDeleted() {
		Order order = createDummyOrder(GENERATED_ORDER_ID);
		order.getAddress().setId(GENERATED_ADDRESS_ID);
		
		try {
			when(orderRepository.findById(GENERATED_ORDER_ID)).thenReturn(Optional.of(order));
			when(addressService.delete(GENERATED_ADDRESS_ID)).thenThrow(new ServiceException("E03", "Address doesn't exist"));
			
			orderService.delete(GENERATED_ORDER_ID);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), ErrorCodes.GENERIC_ERROR);
		}

	}
	
	@Test
	public void shouldThrowExceptionWhenProductNotDeleted() {
		Order order = createDummyOrder(GENERATED_ORDER_ID);
		order.getAddress().setId(GENERATED_ADDRESS_ID);
		order.getProduct().setId(GENERATED_PRODUCT_ID);
		
		try {
			when(orderRepository.existsById(GENERATED_ORDER_ID)).thenReturn(true);
			when(orderRepository.findById(GENERATED_ORDER_ID)).thenReturn(Optional.of(order));
			when(addressService.delete(GENERATED_ADDRESS_ID)).thenReturn(true);
			when(productService.delete(GENERATED_PRODUCT_ID)).thenThrow(new ServiceException("E03", "Product doesn't exist"));
			
			orderService.delete(GENERATED_ORDER_ID);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), ErrorCodes.GENERIC_ERROR);
		}

	}
	
	@Test
    public void shouldReturnAllOrders() {
		List<OrderDTO> orderDTOs = Collections.singletonList(createDummyOrderDTO(null));
		List<Order> orders = Collections.singletonList(createDummyOrder(null));
		
		when(orderRepository.findAll()).thenReturn(orders);
		when(orderMapper.toDto(ArgumentMatchers.any(Order.class))).thenReturn(createDummyOrderDTO(null));
		
		List<OrderDTO> returnOrders = orderService.getAll();
		
		assertEquals(returnOrders, orderDTOs);
    }
	
	@Test
    public void shouldReturnOrderById() {
		OrderDTO order = createDummyOrderDTO(GENERATED_ORDER_ID);
		
		when(orderRepository.findById(GENERATED_ORDER_ID)).thenReturn(Optional.of(createDummyOrder(null)));
		when(orderMapper.toDto(ArgumentMatchers.any(Order.class))).thenReturn(order);
		
		OrderDTO returnOrder = orderService.get(GENERATED_ORDER_ID);
		
		assertNotNull(returnOrder);
		assertEquals(returnOrder, order);
    }
	
	@Test
    public void whenGivenOrderIdShouldUpdateOrderStatusIfFoundOrder() {
		Order order = createDummyOrder(GENERATED_ORDER_ID);
  
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        boolean status = false;
        try {
			status = orderService.changeStatus(GENERATED_ORDER_ID, "REFUND");
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
        
        assertTrue(status);
    }
	
	private OrderDTO createDummyOrderDTO(UUID uuid) {
		AddressDTO addressDTO = new AddressDTO(null, "Test Address", "Antalya", "Turkey", 7);
		ProductDTO productDTO = new ProductDTO(null, "http://product.images.com/ghsagd8deevdv", "First Image");
		return new OrderDTO(uuid, GENERATED_CUSTOMER_ID, 2, 100.5, "SUCCESS", addressDTO, productDTO);
	}
	
	private Order createDummyOrder(UUID uuid) {
		Address address = new Address(null, "Test Address", "Antalya", "Turkey", 7, false);
		Product product = new Product(null, "http://product.images.com/ghsagd8deevdv", "First Image", false);
		return new Order(uuid, GENERATED_CUSTOMER_ID, 2, 100.5, "SUCCESS", address, product);
	}
}
