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
import com.ayrton.project.services.exceptions.DatabaseException;
import com.ayrton.project.services.exceptions.ResourceNotFoundException;

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
	public Boolean findByOrder(LocalDate date, Client client) {
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
	
	public Order insert(Order c) {
		return repository.save(c);
	}
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DatabaseException(e.getMessage());
		}
	}
	
}
