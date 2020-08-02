package com.andriypyzh.controllers;

import com.andriypyzh.entities.Item;
import com.andriypyzh.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("items")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("")
    public List<Item> getItems(){
        return itemService.getItems();
    }

    @GetMapping(value = "",params = "itemName")
    public Object getItemByNameAtLowestPrice(@RequestParam String itemName){
        Item item = itemService.getItemAtLowestPrice(itemName);
        if (item == null){
            return itemService.getItems();
        }else{
            return item;
        }
    }

    @GetMapping(value = "/{id}")
    public Item getItem(@PathVariable Long id){
        return itemService.getItemById(id);
    }

    @PostMapping("")
    public Item createItem(@RequestBody Item item){
        return itemService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
    }

}
