package br.unirio.kipao.repository;

import org.springframework.data.repository.CrudRepository;

import br.unirio.kipao.model.PaymentMethod;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {

}
