package com.andriypyzh.services;

import com.andriypyzh.entities.Item;
import com.andriypyzh.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService( ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<Item> getItems(){
        return itemRepository.findAll();
    }

    public Item getItemById(Long id){
        return itemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Item getItemAtLowestPrice(String itemName){
        return itemRepository.findFirstByNameOrderByPriceAsc(itemName);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
