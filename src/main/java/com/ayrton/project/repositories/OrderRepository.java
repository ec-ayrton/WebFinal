package com.ayrton.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
