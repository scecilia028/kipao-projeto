package br.unirio.kipao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unirio.kipao.model.Customer;
import br.unirio.kipao.repository.CustomerRepository;
import br.unirio.kipao.security.SecurityService;
import br.unirio.kipao.security.models.User;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SecurityService securityService;
	
	public Customer getCustomerByLoggedUser() {
		
		return customerRepository.findByUserId(securityService.getUser().getUid());
	}
	
	public void createCustomer(User user) {
		
		Customer customer = customerRepository.findByUserId(user.getUid());
		
		if(customer == null) {
			customer = new Customer();
			customer.setName(user.getName());
			customer.setUserId(user.getUid());
		}

		customerRepository.save(customer);
	}

	public Customer getCustomerById(Long id) {
		return customerRepository.findByIdCustomer(id);
	}

	public Customer createCustomer(Customer customer) {
		Customer userRepo = getCustomerById(customer.getId());
		
		if(userRepo == null) {
			customer.setUserId("qZhaLfUMAZS9DxOpbnq3mdayPsH2"+customer.getName());
			
			return customerRepository.save(customer);
		}
		return userRepo;
	}

	public Optional<Customer> getCustomerByUser(Long id) {
		return customerRepository.findById(id);
	}

}
