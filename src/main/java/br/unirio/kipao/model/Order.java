package br.unirio.kipao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "CustomerOrder")
@Inheritance(strategy = InheritanceType.JOINED)
@NotNull
public class Order extends AbstractEntity {
	
	@NotNull
	private Double total;
	@NotNull
	private Double shippingFare;
	
	private Date created = new Date();
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@NotNull
	private List<Item> items;
	
	@ManyToOne
	@JsonBackReference
	@NotNull
	private Customer customer;
	
	@ManyToOne
	@NotNull
	private Address address;
	
	@ManyToOne
	@NotNull
	private PaymentMethod paymentMethod;
	
	@NotNull
	private String orderState;
	
	private Date dateHour;
	
	private String type = "Entrega";

}
