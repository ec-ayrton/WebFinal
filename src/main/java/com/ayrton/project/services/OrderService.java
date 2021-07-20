package com.ayrton.project.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;
import com.ayrton.project.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	public List<Order> findAll(){
		return repository.findAll();
	}
	public Optional<Order> findById(Long id) {
		Optional<Order> c = repository.findById(id);
		return c;
	}
	public boolean ExistsByOrder(LocalDate date, Client client) {
		List<Order> listOrderByDate = repository.findByDataPedido(date);
		List<Order> listsOrderByClient = repository.findByClient(client);
		
		for(Order ByData:listOrderByDate) {
			for(Order byClient:listsOrderByClient) {
				if(ByData.equals(byClient)) {
					return true;
				}
			}
		}
		return false;
	}
	public Order findByOrder(LocalDate date, Client client) {
		List<Order> listOrderByDate = repository.findByDataPedido(date);
		List<Order> listsOrderByClient = repository.findByClient(client);
		
		for(Order ByData:listOrderByDate) {
			for(Order byClient:listsOrderByClient) {
				if(ByData.equals(byClient)) {
					return ByData;
				}
			}
		}
		return null;
	}
	
	public Order insert(Order c) {
		return repository.save(c);
	}
	public Object delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return 404;
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			return 400;
		}
		return 200;
	}
	
}
