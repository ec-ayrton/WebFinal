//package com.ayrton.project.config;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import com.ayrton.project.entities.Client;
//import com.ayrton.project.entities.Order;
//import com.ayrton.project.entities.OrderItem;
//import com.ayrton.project.entities.Product;
//import com.ayrton.project.repositories.ClientRepository;
//import com.ayrton.project.repositories.OrderItemRepository;
//import com.ayrton.project.repositories.OrderRepository;
//import com.ayrton.project.repositories.ProductRepository;
//
//@Configuration
//@Profile("test")
//public class TestConfig implements CommandLineRunner{
//
//	@Autowired
//	private ClientRepository clientRepository;
//	
//	
//	@Autowired
//	private ProductRepository productRepository;
//	
//	@Autowired
//	private OrderRepository orderRepository;
//	
//	@Autowired
//	private OrderItemRepository orderItemRepository;
//	
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		Client c1 = new Client("Ana",    "01759767328", "8899001120");
//		Client c2 = new Client("Borges", "51418818305", "8899001121");
//		Client c3 = new Client("Carlos", "35262405312", "8899001122");
//		Client c4 = new Client("Duda",   "74412219356", "8899001123");
//	
//		clientRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
//		
//		Product p1 = new Product("agua", 2.0 ,"500ml");
//		Product p2 = new Product("agua", 3.0 ,"1l");
//		Product p3 = new Product("coca", 3.0 ,"lata 350ml");
//		Product p4 = new Product("coca", 5.0 ,"500ml");
//		Product p5 = new Product("cerveja", 3.0 ,"lata 350ml");
//		
//		productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
//		
//		Order o1 = new Order(LocalDate.parse("2009-02-11"),c1);
//		Order o2 = new Order(LocalDate.parse("2009-02-12"),c1);
//		Order o3 = new Order(LocalDate.parse("2009-02-13"),c2);
//		Order o4 = new Order(LocalDate.parse("2009-02-14"),c3);
//		Order o5 = new Order(LocalDate.parse("2009-02-15"),c3);
//		
//		orderRepository.saveAll(Arrays.asList(o1,o2,o3,o4,o5));
//		orderRepository.save(o1);
//		OrderItem oi1 = new OrderItem(o1, p5, 10);
//		OrderItem oi2 = new OrderItem(o2, p3, 2);
//		OrderItem oi3 = new OrderItem(o3, p1, 1);
//		OrderItem oi4 = new OrderItem(o4, p2, 1);
//		OrderItem oi5 = new OrderItem(o5, p4, 1);
//		OrderItem oi6 = new OrderItem(o1, p1, 1);
//		OrderItem oi7 = new OrderItem(o2, p1, 1);
//		OrderItem oi8 = new OrderItem(o3, p5, 1);
//		OrderItem oi9 = new OrderItem(o1, p3, 1);
//		
//		orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3,oi4,oi5,oi6,oi7,oi8,oi9));
//	}
//
//}
