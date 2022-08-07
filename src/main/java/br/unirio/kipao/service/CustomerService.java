package br.unirio.kipao.service;

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

}
