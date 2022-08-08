package br.unirio.kipao.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@PrimaryKeyJoinColumn(name="id")
public class PersonalizedProduct extends Product{
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PersonalizationOption", 
	joinColumns = { @JoinColumn(name = "product_id") },
	inverseJoinColumns = { @JoinColumn(name = "personalization_id")})
	private List<Personalization> personalizations;

}
