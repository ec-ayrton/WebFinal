package com.ayrton.project.Controllers;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.ayrton.project.entities.Product;
import com.ayrton.project.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
public class ProductControllerTest {
	
	private final String urlHost = "http://localhost:8080";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	
	@BeforeEach
	public void limpaBaseDeDados() {
		productRepository.deleteAll();
	}
	
	
	@Test
	@DisplayName("adicionar um novo produto de forma bem sucedida.")
	void addProductTest() throws JsonProcessingException, Exception {
	
		Product produtoNovo= new Product("Coxinha",2.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Product> produto = productRepository.findByName(produtoNovo.getName());
		
		assertTrue(produto.isPresent());
	}
	@Test
	@DisplayName("adicionar um novo produto repetido.")
	void addTheSameProductTest() throws JsonProcessingException, Exception {
	
		Product produtoNovo= new Product("Coxinha",2.0,"salgado de frango.");
		Product produtoNovo2= new Product("Coxinha",2.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo2))).andExpect(MockMvcResultMatchers.status().isConflict());                                                                                                          

	
	}
	@Test
	@DisplayName("Obter um produto por id.")
	void getProductByIDSucess() throws JsonProcessingException, Exception {
		
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
		
		assertTrue(response.getId().equals(produto.get().getId()));
		assertTrue(response.getName().equals(produto.get().getName()));
		assertTrue(response.getPrice().equals(produto.get().getPrice()));
		assertTrue(response.getDescription().equals(produto.get().getDescription()));
	}
	@Test
	@DisplayName("FALHAR ao Obter um produto por id INEXISTENTE.")
	void FailgetProduct() throws JsonProcessingException, Exception {
		
		//
		mockMvc
				.perform(get(urlHost+"/produtos/{codigo}", 1).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();                                                                                                         
		
	}
	@Test
	@DisplayName("Obter todos os produtos.")
	void getAllProducts() throws JsonProcessingException, Exception {
		
		//
		Product produtoNovo= new Product("Coxinha",2.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          	
		//
		Product produtoNovo2= new Product("Coxinha Grande",3.0,"salgado de frango.");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo2))).andExpect(MockMvcResultMatchers.status().isCreated()); 
		
		//
		Product produtoNovo3= new Product("Cento de coxinha",33.0,"100 unidades de coxinhas pequenas");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(produtoNovo3))).andExpect(MockMvcResultMatchers.status().isCreated()); 
		//
		MvcResult result = mockMvc
				.perform(get(urlHost+"/produtos").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();                                                                                                         
		String resultContent = result.getResponse().getContentAsString();
		List<Product> list = objectMapper.readValue(resultContent, new TypeReference<List<Product>>(){});
		assertTrue(list.size()==3);
		
	}
}













