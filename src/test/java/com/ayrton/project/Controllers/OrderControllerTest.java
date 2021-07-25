package com.ayrton.project.Controllers;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

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

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;
import com.ayrton.project.entities.DTO.ClientForm;
import com.ayrton.project.entities.DTO.OrderForm;
import com.ayrton.project.entities.DTO.OrderResponse;
import com.ayrton.project.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
public class OrderControllerTest {
	
private final String urlHost = "http://localhost:8080";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	OrderRepository orderRepository;
		
	
	@BeforeEach
	public void limpaBaseDeDados() {
		orderRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Cadastrar Ordem de pedido com sucesso.")
	void addOrderSucessTest() throws JsonProcessingException, Exception {
		ClientForm clientNovo = new ClientForm("Ana",    "01759767328", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), "01759767328");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
	}
	@Test
	@DisplayName("Cadastrar Ordem de pedido repetidamente.")
	void addOrdertwiceFailTest() throws JsonProcessingException, Exception {
		ClientForm clientNovo = new ClientForm("Ana",    "01759767328", "8899001120");;
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), "01759767328");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		OrderForm orderForm2 = new OrderForm(LocalDate.parse("2020-03-30"), "01759767328");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm2))).andExpect(MockMvcResultMatchers.status().isConflict());                                                                                                          
	
	}
	@Test
	@DisplayName("Cadastrar Ordem sem cliente válido/CPF não cadastrado.")
	void addOrderFailTest() throws JsonProcessingException, Exception {
		ClientForm clientNovo = new ClientForm("Ana",    "01759767328", "8899001120");;
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
			
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), "51418818305");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isNotFound());                                                                                                          
	
		OrderForm orderForm2 = new OrderForm(LocalDate.parse("2020-03-30"), "5141");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm2))).andExpect(MockMvcResultMatchers.status().isNotFound());                                                                                                          
		
	}
	@Test
	@DisplayName("Obter uma ordem de pedido por id com sucesso.")
	void getOrderByIDSucess() throws JsonProcessingException, Exception {
		//cadastro um cliente pra ser usado no cadastramento da ordem do pedido.
		ClientForm clientNovo = new ClientForm("Ana",    "01759767328", "8899001120");;
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//CLIENTE CADASTRADO
		
		//AGORA VOU POSTAR A ORDEM DE PEDIDO COM O CPF DO CLIENTE ACIMA CADASTRADO.
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), "01759767328");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//ORDEM DE PEDIDO POSTADA.
		//
		Client client = clientNovo.toModel();
		Order orderFromDb = null;
		//
		List<Order> listOrderByDate = orderRepository.findByDataPedido(orderForm.getDataPedido());
		//

		for(Order ByData:listOrderByDate) {
			if(ByData.getClient().getCPF().equals(client.getCPF())) {
				orderFromDb = ByData;
			}	
		}
		
		//
		MvcResult result = mockMvc
				.perform(get(urlHost+"/pedidos/{codigo}", orderFromDb.getId() ).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		
		String resultContent = result.getResponse().getContentAsString();
		OrderResponse response = objectMapper.readValue(resultContent, OrderResponse.class);
		
		assertTrue(response.getId()==(orderFromDb.getId()));
		assertTrue(response.getItems().equals(orderFromDb.getItems()));
		assertTrue(response.getDataPedido().equals(orderFromDb.getDataPedido()));
		assertTrue(response.getTotal().equals(orderFromDb.getTotal()));
	}
	@Test
	@DisplayName("Obter uma ordem de pedido com ID inexistente")
	void getOrderByIDFail() throws JsonProcessingException, Exception {
		//cadastro um cliente pra ser usado no cadastramento da ordem do pedido.
		ClientForm clientNovo = new ClientForm("Ana",    "01759767328", "8899001120");;
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//CLIENTE CADASTRADO
		
		//AGORA VOU POSTAR A ORDEM DE PEDIDO COM O CPF DO CLIENTE ACIMA CADASTRADO.
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), "01759767328");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//ORDEM DE PEDIDO POSTADA.
			mockMvc
				.perform(get(urlHost+"/pedidos/{codigo}", 20 ).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());	
	}
	@Test
	@DisplayName("Obter todas as ordens de pedido")
	void getAllOrders() throws JsonProcessingException, Exception{
		// 
		//POVOANDO A ORDEM
		//
		ClientForm clientNovo = new ClientForm("Ana",    "01759767328", "8899001120");;
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		
		OrderForm orderForm = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//
		ClientForm clientNovo2 = new ClientForm("Borges","51418818305", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo2))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		
		OrderForm orderForm2 = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo2.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm2))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//
		ClientForm clientNovo3 = new ClientForm("Carlos","35262405312", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo3))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		
		OrderForm orderForm3 = new OrderForm(LocalDate.parse("2020-03-30"), clientNovo3.getCPF());
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/pedidos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(orderForm3))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		//
		MvcResult result = mockMvc
				.perform(get(urlHost+"/pedidos").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();                                                                                                         
		String resultContent = result.getResponse().getContentAsString();
		List<OrderResponse> list = objectMapper.readValue(resultContent, new TypeReference<List<OrderResponse>>(){});
		assertTrue(list.size()==3);
	}
}
