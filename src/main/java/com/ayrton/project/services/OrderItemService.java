package com.ayrton.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayrton.project.entities.OrderItem;
import com.ayrton.project.repositories.OrderItemRepository;

@Service
public class OrderItemService {
	@Autowired
	OrderItemRepository repository;
	public List<OrderItem> findAll(){
		return repository.findAll();
	}
	public OrderItem insert(OrderItem oi) {
		return repository.save(oi);
	}
	
}
