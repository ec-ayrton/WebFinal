package com.ayrton.project.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Product;
import com.ayrton.project.repositories.ClientRepository;
import com.ayrton.project.repositories.ProductRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Client c1 = new Client(null,"ayrton","00011122233","889900112233");
		Client c2 = new Client(null,"rogerio","00211122233","889900112234");
		Client c3 = new Client(null,"roger","00311122233","889900112235");
		Client c4 = new Client(null,"marilia","00411122233","889900112236");
	
		clientRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		Product p1 = new Product(null,"agua", 2.0 ,"500ml");
		Product p2 = new Product(null,"agua", 3.0 ,"1l");
		Product p3 = new Product(null,"coca", 3.0 ,"lata 350ml");
		Product p4 = new Product(null,"coca", 5.0 ,"500ml");
		Product p5 = new Product(null,"cerveja", 3.0 ,"lata 350ml");
		
		productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
	}

}
