package br.unirio.kipao.repository;

import br.unirio.kipao.helper.CustomRepository;
import br.unirio.kipao.model.Order;

public interface OrderRepository extends CustomRepository<Order, Long> {
	
	Iterable<Order> findOrdersByCustomerUserId(String userId);

}
