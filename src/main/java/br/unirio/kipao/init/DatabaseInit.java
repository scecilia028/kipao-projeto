package br.unirio.kipao.init;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import br.unirio.kipao.model.Address;
import br.unirio.kipao.model.Customer;
import br.unirio.kipao.model.Item;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.OrderSubscribe;
import br.unirio.kipao.model.PaymentMethod;
import br.unirio.kipao.model.Personalization;
import br.unirio.kipao.model.PersonalizationOption;
import br.unirio.kipao.model.PersonalizedProduct;
import br.unirio.kipao.model.Product;
import br.unirio.kipao.repository.AddressRepository;
import br.unirio.kipao.repository.CustomerRepository;
import br.unirio.kipao.repository.ItemRepository;
import br.unirio.kipao.repository.OrderRepository;
import br.unirio.kipao.repository.PaymentMethodRepository;
import br.unirio.kipao.repository.PersonalizationOptionRepository;
import br.unirio.kipao.repository.PersonalizationRepository;
import br.unirio.kipao.repository.ProductRepository;

@Service
public class DatabaseInit implements CommandLineRunner{

	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private PersonalizationRepository personalizationRepository;
	
	@Autowired
	private PersonalizationOptionRepository personalizationOptionRepository;

	
	@Value("create")
	private String ddlMode;

	@Override
	public void run(String... args) throws Exception {
		
		if(ddlMode.contains("create"))
		{	
			PersonalizedProduct cake = new PersonalizedProduct();
			cake.setMetric("Unitario");
			cake.setName("Bolo Personalizado");
			cake.setUnitPrice(new Double("10.00"));
			
			Personalization personalizacao = new Personalization();
			personalizacao.setName("Chocolate");
			personalizacao.setPersonalizationType("Cobertura");
			
			personalizationRepository.save(personalizacao);
			
			Personalization personalizacao2 = new Personalization();
			personalizacao2.setName("Morango");
			personalizacao2.setPersonalizationType("Recheio");
			
			personalizationRepository.save(personalizacao2);
			
			PersonalizationOption opcaoPersonalizacao = new PersonalizationOption();
			opcaoPersonalizacao.setPersonalization(personalizacao);
			opcaoPersonalizacao.setProduct(cake);
			opcaoPersonalizacao.setUnitPrice(new Double("2.00"));
			
			productRepository.save(cake);
			personalizationOptionRepository.save(opcaoPersonalizacao);
			
			Product kit = new Product();
			kit.setMetric("Unitario");
			kit.setName("Pao de hamburguer");
			kit.setUnitPrice(new Double("0.75"));
			
			productRepository.save(kit);			
			
			Product cafe = new Product();
			cafe.setMetric("Unitario");
			cafe.setName("Cafe");
			cafe.setUnitPrice(new Double("2.5"));
			
			productRepository.save(cafe);

			PaymentMethod paymentMethod = new PaymentMethod();
			paymentMethod.setName("Cartao de Credito");
			
			paymentMethodRepository.save(paymentMethod);

			Customer customer = new Customer();
			customer.setCellphone("99999-9999");
			customer.setPhone("3333-3333");
			customer.setName("Fulano");
			customer.setUserId("qZhaLfUMAZS9DxOpbnq3mdayPsH2");
			customer.setCpf("11111111111");
		
			customerRepository.save(customer);
			
			Address address = new Address();
			address.setAddress("Rua das Rosas");
			address.setNumber("123");
			address.setComplement("Casa 3");
			address.setNeighborhood("Horto");
			address.setPostalCode("11111111");
			address.setState("Rio das Flores");
			address.setCity("Lago das Hortencias");
			address.setCustomer(customer);
			
			addressRepository.save(address);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			Order scheduler = new Order();
			scheduler.setCustomer(customer);
			scheduler.setPaymentMethod(paymentMethod);
			scheduler.setShippingFare(new Double("5.00"));
			scheduler.setTotal(new Double("14.00"));
			scheduler.setDateHour(sdf.parse("22/07/2021 10:59"));
			scheduler.setAddress(address);
			scheduler.setOrderState("Aguardando confirmacao");
			scheduler.setType("Agendamento");
			
			Item itemBasico = new Item();
			itemBasico.setProduct(cake);
			itemBasico.setQuantity(1);
			itemBasico.setUnitPrice(new Double("14.00"));
			itemBasico.setNote("Rechear bastante");
			itemBasico.setTotalPrice(new Double("14.00"));
			itemBasico.setOrder(scheduler);
			
			orderRepository.save(scheduler);
			itemRepository.save(itemBasico);						
			
			Product product = new Product();
			product.setMetric("Unitario");
			product.setUnitPrice(new Double("3.00"));
			product.setName("Coca-Cola Lata");
			
			productRepository.save(product);
			
			OrderSubscribe orderSubscribe = new OrderSubscribe();
			orderSubscribe.setCustomer(customer);
			orderSubscribe.setAddress(address);
			orderSubscribe.setPaymentMethod(paymentMethod);
			orderSubscribe.setShippingFare(new Double("0.00"));
			orderSubscribe.setTotal(new Double("5.00"));
			orderSubscribe.setRecurring("3 dias");
			orderSubscribe.setOrderState("Finalizado");
			orderSubscribe.setType("Assinatura");
			
			Item itemBasico2 = new Item();
			itemBasico2.setProduct(product);
			itemBasico2.setQuantity(2);
			itemBasico2.setUnitPrice(new Double("5.00"));
			itemBasico2.setTotalPrice(new Double("10.00"));
			itemBasico2.setOrder(orderSubscribe);
			itemBasico2.setNote("Rechear muito");
			
			orderRepository.save(orderSubscribe);			
			itemRepository.save(itemBasico2);
			
			Order orderPickup = new Order();
			orderPickup.setCustomer(customer);
			orderPickup.setAddress(address);
			orderPickup.setPaymentMethod(paymentMethod);
			orderPickup.setShippingFare(new Double("0.00"));
			orderPickup.setTotal(new Double("5.00"));
			orderPickup.setDateHour(sdf.parse("22/07/2021 10:59"));
			orderPickup.setOrderState("Em andamento");
			orderPickup.setType("Retirada");
			
			Item itemPersonalizado = new Item();
			itemPersonalizado.setProduct(cake);
			itemPersonalizado.setOrder(orderPickup);
			itemPersonalizado.setQuantity(1);
			itemPersonalizado.setUnitPrice(new Double("5.00"));
			itemPersonalizado.setTotalPrice(new Double("10.00"));
			itemPersonalizado.setNote("Pouco recheio");
						
			itemPersonalizado.setPersonalizationOptions(Arrays.asList(opcaoPersonalizacao));
			
			orderRepository.save(orderPickup);			
			itemRepository.save(itemPersonalizado);
		}
	}

}
