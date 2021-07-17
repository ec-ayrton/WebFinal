package com.ayrton.project.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ayrton.project.entities.Client;
import com.ayrton.project.entities.Order;
import com.ayrton.project.entities.OrderItem;
import com.ayrton.project.entities.Product;
import com.ayrton.project.repositories.ClientRepository;
import com.ayrton.project.repositories.OrderItemRepository;
import com.ayrton.project.repositories.OrderRepository;
import com.ayrton.project.repositories.ProductRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Client c1 = new Client(null,"ayrton","00011122233","8899001120");
		Client c2 = new Client(null,"rogerio","00000000000","8899001121");
		Client c3 = new Client(null,"roger","00000000001","8899001122");
		Client c4 = new Client(null,"marilia","00411122233","8899001123");
	
		clientRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		Product p1 = new Product(null,"agua", 2.0 ,"500ml");
		Product p2 = new Product(null,"agua", 3.0 ,"1l");
		Product p3 = new Product(null,"coca", 3.0 ,"lata 350ml");
		Product p4 = new Product(null,"coca", 5.0 ,"500ml");
		Product p5 = new Product(null,"cerveja", 3.0 ,"lata 350ml");
		
		productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
		
		Order o1 = new Order(null,  Instant.parse("2020-01-30T14:00:01Z")  ,c1);
		Order o2 = new Order(null,Instant.parse("2020-01-30T16:10:00Z") ,c1);
		Order o3 = new Order(null,Instant.parse("2020-01-30T17:00:00Z") ,c2);
		Order o4 = new Order(null,Instant.parse("2020-01-30T18:30:00Z") ,c3);
		Order o5 = new Order(null,Instant.parse("2020-01-30T19:30:10Z") ,c3);
		
		orderRepository.saveAll(Arrays.asList(o1,o2,o3,o4,o5));
		
		OrderItem oi1 = new OrderItem(o1, p5, 10, p5.getPrice());
		OrderItem oi2 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi3 = new OrderItem(o3, p1, 1, p1.getPrice());
		OrderItem oi4 = new OrderItem(o4, p2, 1, p2.getPrice());
		OrderItem oi5 = new OrderItem(o5, p4, 1, p4.getPrice());
		OrderItem oi6 = new OrderItem(o1, p1, 1, p1.getPrice());
		OrderItem oi7 = new OrderItem(o2, p1, 1, p1.getPrice());
		OrderItem oi8 = new OrderItem(o3, p5, 1, p5.getPrice());
		OrderItem oi9 = new OrderItem(o1, p3, 1, p3.getPrice());
		
		orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3,oi4,oi5,oi6,oi7,oi8,oi9));
	}

}
