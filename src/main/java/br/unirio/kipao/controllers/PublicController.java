package br.unirio.kipao.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unirio.kipao.exceptions.ProductAlreadyDeleted;
import br.unirio.kipao.exceptions.ProductAlreadyRegistered;
import br.unirio.kipao.model.Customer;
import br.unirio.kipao.model.Item;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.Product;
import br.unirio.kipao.model.dto.ErrorDTO;
import br.unirio.kipao.security.roles.IsCustomer;
import br.unirio.kipao.security.roles.IsEmployee;
import br.unirio.kipao.service.CustomerService;
import br.unirio.kipao.service.ItemService;
import br.unirio.kipao.service.OrderService;
import br.unirio.kipao.service.ProductService;

@RestController
@RequestMapping("public")
@SuppressWarnings("rawtypes")
public class PublicController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("data")
	public String getPublicData() {

		return "You have accessed public data from spring boot";
	}
	
	@GetMapping("/product/{name}")
	public ResponseEntity<ArrayList<Product>> getProduct(@PathVariable(value = "name") String nameProduct) {
		ArrayList<Product> product = productService.getProduct(nameProduct); 
		if (product != null) {
			return ResponseEntity.ok(product);
		}
		return ResponseEntity.notFound().build();
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
	
	@PutMapping("item")
	public ResponseEntity createItem(@RequestBody Item item) {
		try {
//			Customer customer = customerService.getCustomerByLoggedUser();
			Customer customer = customerService.getCustomerById(Long.parseLong("8"));
			return ResponseEntity.ok(itemService.saveItem(item, customer));			
		} catch (Exception e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@DeleteMapping("item")
	public ResponseEntity deleteItem(@RequestBody Item item) {
		try {
			return ResponseEntity.ok(itemService.deleteItem(item));
			
		} catch (Exception e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			
			return ResponseEntity.badRequest().body(error);
		}
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
		
	@DeleteMapping("product/{name}")
	public ResponseEntity deleteProduct(@PathVariable(value = "name") String nameProduct) {
		try {
			return ResponseEntity.ok(productService.deleteProduct(nameProduct));
			
		} catch (ProductAlreadyDeleted e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@PutMapping("customer")
	public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
				
		try {
			return ResponseEntity.ok().body(customerService.createCustomer(customer));
		}catch(Exception e ) {
			ErrorDTO error = new ErrorDTO("O usuário nao pode ser cadadstrado");
			return ResponseEntity.badRequest().body(error);
		}
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable(value = "id") Long id) {
		try {
			return ResponseEntity.ok(customerService.getCustomerById(id));
		}catch(Exception e ) {
			ErrorDTO error = new ErrorDTO("O usuário não foi encontrado");
			return ResponseEntity.badRequest().body(error);
		}
	}
}
