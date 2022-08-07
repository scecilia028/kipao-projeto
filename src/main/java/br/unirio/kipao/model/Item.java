package br.unirio.kipao.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item extends AbstractEntity {
	
	@ManyToOne
	private Product product;
	
	private Integer quantity;
	private Double unitPrice;
	
	@Transient
	private Double totalPrice;
	
	@OneToOne
	@JsonBackReference
	private Order order;
	
	@ManyToMany
	private List<PersonalizationOption> personalizationOptions;
	
	public Double getTotalPrice() {
		return 0.00;
	}

}
