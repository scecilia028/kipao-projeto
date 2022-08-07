package br.unirio.kipao.repository;

import org.springframework.data.repository.CrudRepository;

import br.unirio.kipao.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
