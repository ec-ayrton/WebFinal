package com.ayrton.project.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ayrton.project.entities.Client;
import com.ayrton.project.repositories.ClientRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Client c1 = new Client(null,"ayrton","00011122233","889900112233");
		Client c2 = new Client(null,"rogerio","00211122233","889900112234");
		Client c3 = new Client(null,"roger","00311122233","889900112235");
		Client c4 = new Client(null,"marilia","00411122233","889900112236");
	
		clientRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
	}

}
