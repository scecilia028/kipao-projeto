package br.unirio.kipao.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unirio.kipao.exceptions.OrderNotExists;
import br.unirio.kipao.model.Customer;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.OrderSubscribe;
import br.unirio.kipao.model.PushNotificationRequest;
import br.unirio.kipao.repository.OrderRepository;
import br.unirio.kipao.security.SecurityService;
import br.unirio.kipao.security.models.User;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private CustomerService costumerService;
	
	@Autowired
	private PushNotificationService pushNotificationService;
	
	public Order saveCustomerOrder(Order order) {
		
		Customer customer = costumerService.getCustomerByLoggedUser();
		
		order.setCustomer(customer);
		order.getItems().forEach(item -> item.setOrder(order));
		
		Order savedOrder = orderRepository.save(order);
		orderRepository.refresh(savedOrder);
		
		return savedOrder;
	}
	
	public Order saveOrderSubscribe(OrderSubscribe order) {
		
		Customer customer = costumerService.getCustomerByLoggedUser();
		
		order.setCustomer(customer);
		order.setType("Assinatura");
		order.getItems().forEach(item -> item.setOrder(order));
		
		OrderSubscribe savedOrder = orderRepository.save(order);
		orderRepository.refresh(savedOrder);
		
		return savedOrder;	
	}
	
	public Order updateOrderStatus(Order order) throws OrderNotExists {
		
		try {
			Order orderFinded = orderRepository.findById(order.getId()).get();
			
			orderFinded.setOrderState(order.getOrderState());
			
			orderRepository.save(orderFinded);
			
			pushNotificationService.sendPushNotification(createPushNotification(orderFinded));
			
			return orderFinded;
		}
		catch (NoSuchElementException exception) {
			throw new OrderNotExists();
		}
		
	}

	public Iterable<Order> getOrdersByCustomer()
	{
		User user = securityService.getUser();
		
		return orderRepository.findOrdersByCustomerUserId(user.getUid());
	}
	
	public Iterable<Order> getOrders()
	{
		return orderRepository.findAll();
	}
	
	public PushNotificationRequest createPushNotification(Order order) {
		
		PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
		pushNotificationRequest.setTitle("O status do seu pedido foi atualizado");
		pushNotificationRequest.setMessage("O pedido #" + order.getId() + " agora está " + order.getOrderState());
		pushNotificationRequest.setToken("d5hpqTikN3guN74NLbPbhd:APA91bHabzUx0Z8upKPh-gtcGWO73HdK1raXFauCh444ncU0VQ0_UhhBG6JOxkbXY4kKvKyBRGwBOYrCgD4CH-OIZ2Z6EX4a8PhhJxwNvg71PjxdZwuuz2ojVZf_wuFJSDze2plp4riG");
		
		return pushNotificationRequest;
		
	}

}
