package br.unirio.kipao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends AbstractEntity {
	
	private String name;
	private String userId;
	
	@Column(unique = true)
	private String cpf;

}
