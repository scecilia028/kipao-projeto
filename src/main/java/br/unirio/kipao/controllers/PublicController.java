package br.unirio.kipao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unirio.kipao.exceptions.ProductAlreadyRegistered;
import br.unirio.kipao.model.Product;
import br.unirio.kipao.model.dto.ErrorDTO;
import br.unirio.kipao.service.ProductService;

@RestController
@RequestMapping("public")
@SuppressWarnings("rawtypes")
public class PublicController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("data")
	public String getPublicData() {

		return "You have accessed public data from spring boot";
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

}
