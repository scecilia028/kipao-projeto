package br.unirio.kipao.repository;

import org.springframework.data.repository.CrudRepository;

import br.unirio.kipao.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	Customer findByUserId(String userId);

}
