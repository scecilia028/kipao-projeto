package br.unirio.kipao.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NotNull
@PrimaryKeyJoinColumn(name="id")
public class Customer extends Person {
	
	private String phone;
	private String cellphone;
	
	@OneToMany(mappedBy = "customer")
	@JsonManagedReference
	private List<Address> addresses;

}
