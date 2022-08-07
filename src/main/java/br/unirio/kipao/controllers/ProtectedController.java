package br.unirio.kipao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;

import br.unirio.kipao.exceptions.ProductAlreadyDeleted;
import br.unirio.kipao.exceptions.ProductAlreadyRegistered;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.Product;
import br.unirio.kipao.model.dto.ErrorDTO;
import br.unirio.kipao.security.SecurityService;
import br.unirio.kipao.service.OrderService;
import br.unirio.kipao.service.ProductService;

@RestController
@RequestMapping("protected")
@SuppressWarnings("rawtypes")
public class ProtectedController {

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("data")
	public String getProtectedData() throws FirebaseMessagingException {
		String name = securityService.getUser().getName();

		return name.split("\\s+")[0] + ", you have accessed protected data from spring boot";
	}

	@GetMapping("products")
	public ResponseEntity<Iterable<Product>> getProducts()
	{
		return ResponseEntity.ok(productService.getProducts());
	}
	
	@GetMapping("products/market")
	public ResponseEntity<Iterable<Product>> getMarketProducts()
	{
		return ResponseEntity.ok(productService.getMarketProducts());
	}
	
	@GetMapping("orders")
	public ResponseEntity<Iterable<Order>> getOrders() {
		return ResponseEntity.ok(orderService.getOrders());	
	}
	
	@PutMapping("product")
	public ResponseEntity createProduct(@RequestBody Product product) {
		try {
			return ResponseEntity.ok(productService.saveProduct(product));
			
		} catch (ProductAlreadyRegistered e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@DeleteMapping("product")
	public ResponseEntity deleteProduct(@RequestBody Product product) {
		try {
			return ResponseEntity.ok(productService.deleteProduct(product));
			
		} catch (ProductAlreadyDeleted e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			
			return ResponseEntity.badRequest().body(error);
		}
	}

}
