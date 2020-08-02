package com.andriypyzh.controllers;

import com.andriypyzh.entities.Item;
import com.andriypyzh.entities.Order;
import com.andriypyzh.services.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    private List<Item> itemList;

    @BeforeEach
    void setUp() {
        this.itemList = new ArrayList<>();
        itemList.add(new Item(1L, "Apple", 100));
        itemList.add(new Item(2L, "Banana", 200));
        itemList.add(new Item(3L, "PineApple", 300));
    }

    @Test
    void getItems() throws Exception {
        given(itemService.getItems()).willReturn(itemList);

        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(itemList.size())));
    }

    @Test
    void getItemByNameAtLowestPrice() throws Exception {
        Item item = itemList.get(0);
        String itemName = item.getName();

        given(itemService.getItemAtLowestPrice(itemName)).willReturn(item);

        this.mockMvc.perform(get("/items?itemName=" + itemName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(100)));
    }

    @Test
    void getItem() throws Exception {
        Long id = 1L;
        given(itemService.getItemById(id)).willReturn(itemList.get(0));

        this.mockMvc.perform(get("/items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(100)));
    }

    @Test
    void createItem() throws Exception {
        Item item = itemList.get(0);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/items")
                .content(new ObjectMapper().writeValueAsString(item))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItem() throws Exception {
        Long id = 1L;
        given(itemService.getItemById(id)).willReturn(itemList.get(0));

        this.mockMvc.perform(delete("/items/{id}", id))
                .andExpect(status().isOk());
    }
}