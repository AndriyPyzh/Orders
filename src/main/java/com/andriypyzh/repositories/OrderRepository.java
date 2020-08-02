package com.andriypyzh.repositories;

import com.andriypyzh.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    void deleteAllByCreatedAtBefore(LocalDateTime time);

    void deleteByCreatedAtBefore(LocalDateTime time);

    void deleteAllByCreatedAtIsBefore(LocalDateTime time);
}
