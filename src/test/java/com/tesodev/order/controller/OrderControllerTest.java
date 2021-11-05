package com.tesodev.order.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.tesodev.order.dto.AddressDTO;
import com.tesodev.order.dto.OrderDTO;
import com.tesodev.order.dto.ProductDTO;
import com.tesodev.order.rest.OrderController;
import com.tesodev.order.service.impl.OrderService;

@RunWith(MockitoJUnitRunner.Silent.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

	@Mock
    private OrderService orderService;
	
	@InjectMocks
    private OrderController orderController;
	
    private MockMvc mockMvc;
	private final Gson gson = new Gson();
	private final UUID GENERATED_ORDER_ID = UUID.fromString("bce7399d-57f6-4e44-8bd6-001d44518db2");
	private final UUID GENERATED_CUSTOMER_ID = UUID.fromString("32433151-9338-4249-889e-d1701f840c7d");
	
	@Before
    public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
	
	@Test
    public void createOrderAndReturnIdWhenPostMethod() throws Exception {
		OrderDTO order = createDummyOrderDTO(null);
		
		when(orderService.create(order)).thenReturn(GENERATED_ORDER_ID);

        mockMvc.perform(post("/api/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(order)))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.id").value(GENERATED_ORDER_ID.toString()));
    }
	
	@Test
    public void updateOrderAndReturnTrueWhenPutMethod() throws Exception {
		OrderDTO order = createDummyOrderDTO(GENERATED_ORDER_ID);
		
		when(orderService.update(order)).thenReturn(true);

        mockMvc.perform(put("/api/order/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(order)))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.status").value(true));
    }
	
	@Test
    public void deleteOrderAndReturnTrueWhenDeleteMethod() throws Exception {
		
		when(orderService.delete(GENERATED_ORDER_ID)).thenReturn(true);

        mockMvc.perform(delete("/api/order/delete/" + GENERATED_ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.status").value(true));
    }
	
	@Test
    public void listOrdersWhenGetMethod() throws Exception {
		List<OrderDTO> orders = Collections.singletonList(createDummyOrderDTO(GENERATED_ORDER_ID));
		
		when(orderService.getAll()).thenReturn(orders);

        mockMvc.perform(get("/api/order/list")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.orders[0].id").value(GENERATED_ORDER_ID.toString()));
    }
	
	@Test
    public void getOrderByIdWhenGetMethod() throws Exception {
		OrderDTO order = createDummyOrderDTO(GENERATED_ORDER_ID);
		
		when(orderService.get(GENERATED_ORDER_ID)).thenReturn(order);

        mockMvc.perform(get("/api/order/get/" + GENERATED_ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.id").value(GENERATED_ORDER_ID.toString()));
    }
	
	@Test
    public void changeOrderStatusAndReturnTrueWhenGetMethod() throws Exception {
		
		when(orderService.changeStatus(GENERATED_ORDER_ID, "VOID")).thenReturn(true);

        mockMvc.perform(put("/api/order/changeStatus/" + GENERATED_ORDER_ID + "/VOID")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.status").value(true));
    }
	
	private OrderDTO createDummyOrderDTO(UUID uuid) {
		AddressDTO addressDTO = new AddressDTO(null, "Test Address", "Antalya", "Turkey", 7);
		ProductDTO productDTO = new ProductDTO(null, "http://product.images.com/ghsagd8deevdv", "First Image");
		return new OrderDTO(uuid, GENERATED_CUSTOMER_ID, 2, 100.5, "SUCCESS", addressDTO, productDTO);
	}
}
