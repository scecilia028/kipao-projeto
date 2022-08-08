package br.unirio.kipao.repository;

import java.util.ArrayList;

import br.unirio.kipao.helper.CustomRepository;
import br.unirio.kipao.model.Product;

public interface ProductRepository extends CustomRepository<Product, Long> {

	public Iterable<Product> findByNameNotLike(String name);
	
	public ArrayList<Product> findByName(String name);
}
