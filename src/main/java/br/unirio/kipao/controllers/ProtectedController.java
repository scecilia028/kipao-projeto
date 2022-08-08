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

}
