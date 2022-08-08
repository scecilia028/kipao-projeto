package br.unirio.kipao.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;

import br.unirio.kipao.exceptions.ProductAlreadyDeleted;
import br.unirio.kipao.exceptions.ProductAlreadyRegistered;
import br.unirio.kipao.model.Product;
import br.unirio.kipao.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Iterable<Product> getProducts() {
		return productRepository.findAll();
	}
	
	public Iterable<Product> getMarketProducts() {
		return productRepository.findByNameNotLike("Bolo Personalizado");
	}
	
	public Product saveProduct(Product product) throws ProductAlreadyRegistered {
		
		if(Iterables.isEmpty(productRepository.findByName(product.getName()))) {
			Product savedProduct = productRepository.save(product);
			productRepository.refresh(savedProduct);
			
			return savedProduct;
		}
		else throw new ProductAlreadyRegistered();
			
	}
	
	public Product deleteProduct(Product product) throws ProductAlreadyDeleted {
		
		try {
			Product findedProduct = productRepository.findById(product.getId()).get();
			
			if(findedProduct.isDeleted()) {
				throw new ProductAlreadyDeleted();
			}
			
			findedProduct.setDeleted(true);
			
			productRepository.save(findedProduct);
			
			return findedProduct;
			
		}
		catch (NoSuchElementException exception) {
			throw new ProductAlreadyDeleted();
		}			
	}
	
	public Product deleteProduct(String nameProduct) throws ProductAlreadyDeleted {
		
		try {
			Product findedProduct = productRepository.findByName(nameProduct).get(0);
			
			if(findedProduct.isDeleted()) {
				throw new ProductAlreadyDeleted();
			}
			
			findedProduct.setDeleted(true);
			
			productRepository.save(findedProduct);
			
			return findedProduct;
			
		}
		catch (NoSuchElementException exception) {
			throw new ProductAlreadyDeleted();
		}			
	}

	public ArrayList<Product> getProduct(String nameProduct) {
		return productRepository.findByName(nameProduct);
	}

}
