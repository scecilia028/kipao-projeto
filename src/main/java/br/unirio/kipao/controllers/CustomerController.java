package br.unirio.kipao.controllers;

import java.io.IOException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

import br.unirio.kipao.model.Address;
import br.unirio.kipao.model.Customer;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.OrderSubscribe;
import br.unirio.kipao.model.dto.DistanceDTO;
import br.unirio.kipao.model.dto.ErrorDTO;
import br.unirio.kipao.security.roles.IsCustomer;
import br.unirio.kipao.security.roles.IsEmployee;
import br.unirio.kipao.service.AddressService;
import br.unirio.kipao.service.CustomerService;
import br.unirio.kipao.service.OrderService;

@RestController
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("data")
	@IsCustomer
	public String getProtectedData() {
		return "You have accessed customer only data from spring boot";
	}
	
	@GetMapping("")
	@IsCustomer
	public ResponseEntity<Customer> getCustomer() {
		return ResponseEntity.ok(customerService.getCustomerByLoggedUser());
	}

	@PutMapping("order")
	@IsCustomer
	@IsEmployee
	public ResponseEntity<?> saveOrder(@Valid @RequestBody Order order) {
				
		if(order.getAddress() == null) {
			ErrorDTO error = new ErrorDTO("O endereço precisa ser passado no objeto");
			
			return ResponseEntity.badRequest().body(error);
		}
		
		return ResponseEntity.ok().body(orderService.saveCustomerOrder(order));
	}

	@PutMapping("orderSubscribe")
	@IsCustomer
	@IsEmployee
	public ResponseEntity<?> saveOrderSubscribe(@Valid @RequestBody OrderSubscribe order) {
		
		if(order.getAddress() == null) {
			ErrorDTO error = new ErrorDTO("O endereço precisa ser passado no objeto");
			
			return ResponseEntity.badRequest().body(error);
		}
		
		return ResponseEntity.ok().body(orderService.saveOrderSubscribe(order));
	}
	
	@GetMapping("orders")
	@IsCustomer
	public ResponseEntity<Iterable<Order>> getOrdersByCustomer() {	
		
		return ResponseEntity.ok(orderService.getOrdersByCustomer());
	}
	
	@PutMapping("address")
	@IsCustomer
	@IsEmployee
	public ResponseEntity<?> saveAddress(@Valid @RequestBody Address address) {
		
		Customer customer = customerService.getCustomerByLoggedUser();
		address.setCustomer(customer);
		
		DistanceDTO distanceDTO = new DistanceDTO();
		distanceDTO.setAddress(address);
		distanceDTO.setForCalculateArrivalTime(true);
		distanceDTO.setOrigin("Avenida Pasteur, 458 - Urca");
		distanceDTO.setTime(new Date());
		
		try {
			DistanceMatrix distanceMatrix = addressService.getDistance(distanceDTO);
				
			if(distanceMatrix.rows[0].elements[0].distance.inMeters <= 10000) {

				return ResponseEntity.ok().body(addressService.saveAddress(address));
			}
			else {
				ErrorDTO errorDTO = new ErrorDTO("O endereço fica a uma distância maior que 10km");
				return ResponseEntity.badRequest().body(errorDTO);
			}
	
		} catch (ApiException | InterruptedException | IOException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
}
