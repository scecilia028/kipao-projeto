package br.unirio.kipao.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.unirio.kipao.helper.CustomRepository;
import br.unirio.kipao.model.Order;

public interface OrderRepository extends CustomRepository<Order, Long> {
	
	Iterable<Order> findOrdersByCustomerUserId(String userId);

	@Query("from Order where customer_id = :customer")
    public ArrayList<Order> findByCustomerId(@Param("customer")Long customerId);

}
