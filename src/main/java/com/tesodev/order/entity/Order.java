package com.tesodev.order.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "order")
@Where(clause = "closed=false")
public class Order extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -5799681828556770973L;

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "customer_id")
    private UUID customerId;
	
	@Column(name = "quantity")
    private int quantity;
	
	@Column(name = "price")
    private double price;
	
	@Column(name = "status")
    private double status;
	
	@OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
	
	@OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
