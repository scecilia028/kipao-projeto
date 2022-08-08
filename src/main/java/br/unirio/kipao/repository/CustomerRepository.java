package br.unirio.kipao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.unirio.kipao.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	Customer findByUserId(String userId);

	@Query("from Customer where id = :id")
	public Customer findByIdCustomer(@Param("id")Long id);

}
