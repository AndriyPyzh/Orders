package com.andriypyzh.controllers;

import com.andriypyzh.entities.Item;
import com.andriypyzh.entities.Order;
import com.andriypyzh.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        this.orderList = new ArrayList<>();
        this.orderList.add(new Order(1L, 100, 1,
                Arrays.asList(new Item(1L, "apple", 100)), LocalDateTime.now()));
        this.orderList.add(new Order(2L, 200, 1,
                Arrays.asList(new Item(1L, "banana", 200)), LocalDateTime.now()));
        this.orderList.add(new Order(3L, 300, 1,
                Arrays.asList(new Item(1L, "pineapple", 300)), LocalDateTime.now()));
    }

    @Test
    void getOrders() throws Exception {
        given(orderService.getOrders()).willReturn(orderList);

        this.mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(orderList.size())));
    }

    @Test
    void getOrder() throws Exception {
        Long id = 1L;
        given(orderService.getOrderById(id)).willReturn(orderList.get(0));

        this.mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(100)))
                .andExpect(jsonPath("$.quantity", is(1)));
    }


    @Test
    void createOrder() throws Exception {
        Order order = new Order(1L, 100, 1,
                Arrays.asList(new Item(1L, "apple", 100)), null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/orders")
                .content(new ObjectMapper().writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void deleteOrder() throws Exception {
        Long id = 1L;
        given(orderService.getOrderById(id)).willReturn(orderList.get(0));

        this.mockMvc.perform(delete("/orders/{id}", id))
                .andExpect(status().isOk());
    }
}