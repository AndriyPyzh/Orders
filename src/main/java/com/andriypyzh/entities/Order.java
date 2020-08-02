package com.andriypyzh.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private Integer quantity;

    @OneToMany
    private List<Item> items;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (price == null) {
            price = items.stream()
                    .reduce(0,
                            (item1, item2) -> item1 + item2.getPrice(),
                            Integer::sum);
        }
        if (quantity == null) {
            quantity = items.size();
        }
    }
}
