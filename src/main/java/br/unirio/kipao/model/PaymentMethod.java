package br.unirio.kipao.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentMethod extends AbstractEntity {
	
	private String name;

}
