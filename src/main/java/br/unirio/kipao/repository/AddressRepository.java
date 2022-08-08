package br.unirio.kipao.repository;

import org.springframework.data.repository.CrudRepository;

import br.unirio.kipao.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
