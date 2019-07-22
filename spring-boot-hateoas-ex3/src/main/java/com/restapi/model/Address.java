package com.restapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String street;
	private String town;
	private String county;
	private String postcode;
}