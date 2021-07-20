package com.ayrton.project.Controllers;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ayrton.project.entities.OrderItem;
import com.ayrton.project.entities.Product;
import com.ayrton.project.entities.DTO.ClientForm;
import com.ayrton.project.entities.DTO.OrderForm;
import com.ayrton.project.entities.DTO.OrderItemForm;
import com.ayrton.project.repositories.OrderRepository;
import com.ayrton.project.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
public class OrderItemControllerTest {
	private final String urlHost = "http://localhost:8080";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired	
	ProductRepository productRepository;
	
	@BeforeEach
	public void limpaBaseDeDados() {
		orderRepository.deleteAll();
	}
	//
	@Test
	@DisplayName("Cadastrar Item de Pedido com sucesso.")
	void addOrderItemSucessTest() throws JsonProcessingException, Exception {
		//
		ClientForm clientNovo = new ClientForm("Ana","01759767328", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//
		
		Product produtoNovo= new Product("Coxinha Grande",4.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		Optional<Product> produto = productRepository.findByName(produtoNovo.getName());	
		assertTrue(produto.isPresent());
		MvcResult result = mockMvc
				.perform(get(urlHost+"/produtos/{codigo}", produto.get().getId() ).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();                                                                                                         
		String resultContent = result.getResponse().getContentAsString();
		Product response = objectMapper.readValue(resultContent, Product.class);
		
		//
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//
		OrderItemForm itemForm = new OrderItemForm(orderForm.getDataPedido(), clientNovo.getCPF() , response.getId(), 5);
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/itenspedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
	}
	@Test
	@DisplayName("Obter todos os Itens de pedido")
	void getAllOrdersItens() throws JsonProcessingException, Exception{
		//
		//POVOANDO A ORDEM
		//
		//
		Product produtoNovo= new Product("Coxinha",2.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          	
		Optional<Product> produto = productRepository.findByName(produtoNovo.getName());	
		//
		Product produtoNovo2= new Product("Coxinha Grande",3.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo2))).andExpect(MockMvcResultMatchers.status().isCreated()); 
		Optional<Product> produto2 = productRepository.findByName(produtoNovo2.getName());	
		//
		Product produtoNovo3= new Product("Cento de coxinha",33.0,"100 unidades de coxinhas pequenas");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo3))).andExpect(MockMvcResultMatchers.status().isCreated()); 
		Optional<Product> produto3 = productRepository.findByName(produtoNovo3.getName());	
		//
		//
		
		ClientForm clientNovo = new ClientForm("Ana","01759767328", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		ClientForm clientNovo2 = new ClientForm("Borges","51418818305", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo2))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		ClientForm clientNovo3 = new ClientForm("Carlos","35262405312", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo3))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		//
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderForm orderForm2 = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo2.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm2))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderForm orderForm3 = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo3.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm3))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		//
		OrderItemForm itemForm = new OrderItemForm(orderForm.getDataPedido(), clientNovo.getCPF() , produto.get().getId(), 1);
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/itenspedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderItemForm itemForm2 = new OrderItemForm(orderForm.getDataPedido(), clientNovo.getCPF() , produto2.get().getId(), 1);
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/itenspedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemForm2))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderItemForm itemForm3 = new OrderItemForm(orderForm.getDataPedido(), clientNovo2.getCPF() , produto.get().getId(), 5);
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/itenspedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemForm3))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderItemForm itemForm4 = new OrderItemForm(orderForm.getDataPedido(), clientNovo3.getCPF() , produto.get().getId(), 2);
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/itenspedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemForm4))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderItemForm itemForm5 = new OrderItemForm(orderForm.getDataPedido(), clientNovo3.getCPF() , produto3.get().getId(), 1);
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/itenspedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(itemForm5))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		
		MvcResult result = mockMvc
				.perform(get(urlHost+"/itenspedidos").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();                                                                                                         
		String resultContent = result.getResponse().getContentAsString();
		List<OrderItem> list = objectMapper.readValue(resultContent, new TypeReference<List<OrderItem>>(){});
		assertTrue(list.size()==5);
	}
	
	
}
