package com.tesodev.order.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "product")
@Where(clause = "closed=false")
public class Product implements Serializable {

	private static final long serialVersionUID = -7923994713442245143L;

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "image_url")
    private String imageUrl;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "closed")
    private boolean closed;
}
