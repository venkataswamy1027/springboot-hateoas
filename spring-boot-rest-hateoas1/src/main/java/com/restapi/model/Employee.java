package com.restapi.model;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Employee extends ResourceSupport {

	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String email;

}
