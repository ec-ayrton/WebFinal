package com.ayrton.project.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;
import com.ayrton.project.entities.OrderItem;
import com.ayrton.project.entities.Product;
import com.ayrton.project.entities.DTO.OrderItemForm;
import com.ayrton.project.services.ClientService;
import com.ayrton.project.services.OrderItemService;
import com.ayrton.project.services.OrderService;
import com.ayrton.project.services.ProductService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/itenspedidos")
public class OrderItemResource {

	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ClientService clientService;
	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de itens de pedidos"), })
	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll(){
		List<OrderItem> list = orderItemService.findAll();
		return ResponseEntity.ok().body(list);
	}
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Cliente cadastrado com sucesso."),
		    @ApiResponse(code = 400, message = "Erro ao cadastrar item de pedido."),
		})
	@Transactional
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody OrderItemForm itemForm){
		
		Optional<Client> clientFromDb= clientService.findByCPF(itemForm.getCpf());
		if(clientFromDb.isEmpty()) {
			return ResponseEntity.badRequest().body("Cliente não encontrado.");
		}
		Order orderFromDb = orderService.findByOrder(itemForm.getDataPedido(), clientFromDb.get());
		if(orderFromDb==null) {
			return ResponseEntity.badRequest().body("Ordem de pedido  não encontrada.");
		}
		Optional<Product>productFromDb =productService.findById(itemForm.getIdProduto());
		if(productFromDb.isEmpty()) {
			return ResponseEntity.badRequest().body("Produto não encontrado.");
		}
		OrderItem  orderItem= itemForm.toModel(orderFromDb , productFromDb, itemForm.getQuantidadeProduto());
		
		orderItem = orderItemService.insert(orderItem);
		return ResponseEntity.status(HttpStatus.CREATED).body("Item do Pedido cadastrado com sucesso.");
	}
}
