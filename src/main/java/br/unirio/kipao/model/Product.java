package br.unirio.kipao.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends AbstractEntity {
	
	private String name;
	private Double unitPrice;
	
	private String metric;
	
	private boolean deleted;
	
}
