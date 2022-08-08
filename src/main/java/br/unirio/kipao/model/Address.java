package br.unirio.kipao.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NotNull
public class Address extends AbstractEntity {
	
	private String postalCode;
	private String address;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String state;
	
	@ManyToOne
	@JsonBackReference
	private Customer customer;
	
	public String toString() {
		return this.address + ", " 
				+ this.number + " - " 
				+ this.neighborhood + ", "
				+ this.city + " - "
				+ this.state + ", "
				+ this.postalCode;
	}

}
