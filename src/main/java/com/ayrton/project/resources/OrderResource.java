package com.ayrton.project.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;
import com.ayrton.project.entities.DTO.OrderForm;
import com.ayrton.project.entities.DTO.OrderResponse;
import com.ayrton.project.services.ClientService;
import com.ayrton.project.services.OrderService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/pedidos")
public class OrderResource {
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private ClientService clientService;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a lista de ordens de pedidos."),
		})
	@GetMapping
	public ResponseEntity<List<OrderResponse>> findAll(){
		List<Order> list = service.findAll();
		List<OrderResponse>listResponse = new ArrayList<>();
		for(Order response: list) {
			listResponse.add(response.toResponse());
		}
		return ResponseEntity.ok().body(listResponse);
	}
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a ordem de pedido."),
		    @ApiResponse(code = 404, message = "Ordem de pedido  nao encontrada!"),
		})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id){
		Optional<Order> orderOpt = service.findById(id);
		if(orderOpt.isPresent()) {
			OrderResponse orderResponse = orderOpt.get().toResponse();
			//OrderResponse orderResponse = new OrderResponse(orderOpt.get().getId(),orderOpt.get().getDataPedido(),orderOpt.get().getClient().ToResponse(),orderOpt.get().getItems());
			return ResponseEntity.ok().body(orderResponse);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordem de pedido  nao encontrada!");
		}
	}
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Ordem de pedido cadastrada com sucesso."),
		    @ApiResponse(code = 404, message = "Cliente não encontrado."),
		    @ApiResponse(code = 409, message = "Erro ao cadastrar Ordem de pedido."),
		})
	@Transactional
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody OrderForm orderForm){
			
		Optional<Client> clientOpt = clientService.findByCPF(orderForm.getCPF());
		if(clientOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
		}
		if(service.ExistsByOrder(orderForm.getDataPedido(), clientOpt.get())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar Ordem de pedido.");
		}else {
			Order order = orderForm.toModel(clientOpt.get());
			order = service.insert(order);
			order.toString();
			return ResponseEntity.status(HttpStatus.CREATED).body("Ordem de pedido cadastrada.");	
		}		
	}
}
