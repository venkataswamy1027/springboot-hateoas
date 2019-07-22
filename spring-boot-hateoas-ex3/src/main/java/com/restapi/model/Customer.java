package com.restapi.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Customer extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	@OneToOne(cascade = { CascadeType.ALL })
	private Address address;

}