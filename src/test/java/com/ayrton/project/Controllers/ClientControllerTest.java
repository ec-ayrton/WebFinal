package com.ayrton.project.Controllers;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.DTO.ClientForm;
import com.ayrton.project.entities.DTO.OrderResponse;
import com.ayrton.project.repositories.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
public class ClientControllerTest {
	
	private final String urlHost = "http://localhost:8080";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	ObjectMapper objectMapper;
		
	@BeforeEach
	public void limpaBaseDeDados() {
		clientRepository.deleteAll();
	}
	
	@Test
	@DisplayName("adicionar um novo Cliente de forma bem sucedida.")
	void addClientSucessTest() throws JsonProcessingException, Exception {
	
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Client> clientSaved = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved.isPresent());
	}
	@Test
	@DisplayName("adicionar um Cliente  de CPF Repetido.")
	void addSameCPFTwiceTest() throws JsonProcessingException, Exception {
	
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		ClientForm clientNovo2 = new ClientForm("Rogerio","11111111111", "8899235511");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo2))).andExpect(MockMvcResultMatchers.status().isConflict());                                                                                                          
	}
	@Test
	@DisplayName("Obter um Cliente por id.")
	void getClientByIDSucess() throws JsonProcessingException, Exception {
		
		//
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Client> clientSaved = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved.isPresent());
		MvcResult result = mockMvc
				.perform(get(urlHost+"/clientes/{codigo}", clientSaved.get().getId() ).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		
		
		String resultContent = result.getResponse().getContentAsString();
		OrderResponse response = objectMapper.readValue(resultContent, OrderResponse.class);
		
		assertTrue(response.getId()==(clientSaved.get().getId()));
	}
	@Test
	@DisplayName("Falha ao obter um Cliente por id.")
	void getClientByIDFail() throws JsonProcessingException, Exception {
		
		//
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Client> clientSaved = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved.isPresent());
		mockMvc
				.perform(get(urlHost+"/clientes/{codigo}", 10).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());                                                                                                         	
	}
	@Test
	@DisplayName("Obter todos os Clientes.")
	void getAllClientts() throws JsonProcessingException, Exception {
		
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Client> clientSaved = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved.isPresent());
		///////////////////////////
		ClientForm client2 = new ClientForm("Borges", "22222222222", "8899001121");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(client2))).andExpect(MockMvcResultMatchers.status().isCreated()); 
		Optional<Client> clientSaved2 = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved2.isPresent());
		//////////////////////////
		ClientForm client3 = new ClientForm("Carlos", "33333333333", "8899001122");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(client3))).andExpect(MockMvcResultMatchers.status().isCreated()); 
		Optional<Client> clientSaved3 = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved3.isPresent());
		
		////////////////////////////
		MvcResult result = mockMvc
				.perform(get(urlHost+"/clientes").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();                                                                                                         
		String resultContent = result.getResponse().getContentAsString();
		List<Client> list = objectMapper.readValue(resultContent, new TypeReference<List<Client>>(){});
		assertTrue(list.size()==3);
	}
	@Test
	@DisplayName("DELETAR um Cliente por id.")
	void deleteClientByIDSucess() throws JsonProcessingException, Exception {
		
		//
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Client> clientSaved = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved.isPresent());
		mockMvc
				.perform(delete(urlHost+"/clientes/{codigo}", clientSaved.get().getId() ).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());	
	}
	@Test
	@DisplayName("Atualizar Client by Id (apenas telefone permitido, no momento).")
	void UpdateClientByIDSucess() throws JsonProcessingException, Exception {
		
		//
		ClientForm clientNovo = new ClientForm("Ana","11111111111", "8899001120");
		ClientForm clientUpdated = new ClientForm("Ana","11111111111", "8899236689");
		mockMvc.perform(MockMvcRequestBuilders.post(urlHost+"/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientNovo))).andExpect(MockMvcResultMatchers.status().isCreated());                                                                                                          
		
		Optional<Client> clientSaved = clientRepository.findByCPF(clientNovo.getCPF());
		
		assertTrue(clientSaved.isPresent());

		
		////
		mockMvc.perform(MockMvcRequestBuilders.put(urlHost+"/clientes/{codigo}",clientSaved.get().getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientUpdated))).andExpect(MockMvcResultMatchers.status().isOk());                                                                                                          

		////
		
//		mockMvc
//				.perform(put(urlHost+"/clientes/{codigo}", clientSaved.get().getId() ).content(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isNoContent());	
	}
	
}
