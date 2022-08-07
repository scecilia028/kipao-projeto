package br.unirio.kipao.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class PersonalizationOption extends AbstractEntity {
	
	@OneToOne
	@JsonIgnore
	private Product product;
	
	@ManyToOne
	private Personalization personalization;
	
	private Double unitPrice;

}
