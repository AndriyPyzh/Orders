package com.andriypyzh.repositories;

import com.andriypyzh.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findFirstByNameOrderByPriceAsc(String itemName);
}
