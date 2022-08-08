package br.unirio.kipao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import br.unirio.kipao.exceptions.OrderNotExists;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.dto.ErrorDTO;
import br.unirio.kipao.security.SecurityService;
import br.unirio.kipao.security.roles.IsEmployee;
import br.unirio.kipao.security.roles.RoleService;
import br.unirio.kipao.service.OrderService;

@RestController
@RequestMapping("employee")
@SuppressWarnings("rawtypes")
public class EmployeeController {

	@Autowired
	RoleService securityRoleService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	FirebaseAuth firebaseAuth;
	
	@Autowired
	private OrderService orderService;

	@GetMapping("user")
	@IsEmployee
	public UserRecord getUser(@RequestParam String email) throws Exception {
		return firebaseAuth.getUserByEmail(email);
	}
	
	@GetMapping("data")
	@IsEmployee
	public String getSuperData() {
		String name = securityService.getUser().getName();
		return name.split("\\s+")[0] + ", you have accessed super data from spring boot";
	}
	
	@IsEmployee
	@PutMapping("order")
	public ResponseEntity updateOrderState(@RequestBody Order order) {

		try {
			return ResponseEntity.ok().body(orderService.updateOrderStatus(order));
		}
		
		catch (OrderNotExists e) {
			ErrorDTO error = new ErrorDTO(e.getMessage());
			
			return ResponseEntity.badRequest().body(error);
		}
		
	}

}
