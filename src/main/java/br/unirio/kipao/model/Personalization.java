package br.unirio.kipao.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Personalization extends AbstractEntity{
	
	private String name;
	
	private String personalizationType;
}
