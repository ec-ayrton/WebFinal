package com.ayrton.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByDataPedido(LocalDate dataPedido);
	List<Order> findByClient(Client client);
}