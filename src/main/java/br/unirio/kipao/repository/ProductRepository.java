package br.unirio.kipao.repository;

import br.unirio.kipao.helper.CustomRepository;
import br.unirio.kipao.model.Product;

public interface ProductRepository extends CustomRepository<Product, Long> {

	public Iterable<Product> findByNameNotLike(String name);
	
	public Iterable<Product> findByName(String name);
}
