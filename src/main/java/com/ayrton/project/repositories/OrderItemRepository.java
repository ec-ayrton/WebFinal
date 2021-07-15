package com.ayrton.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
