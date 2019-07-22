package com.restapi.ctrl;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.model.Employee;
import com.restapi.service.EmployeeDB;
import com.restapi.service.EmployeeVoList;

@RestController
public class EmployeeController {

	@RequestMapping(value = "/employees")
	public EmployeeVoList getAllEmployees() {
		EmployeeVoList voList = new EmployeeVoList();
		
		for (Employee employee : EmployeeDB.getEmployeeList()) {
			// Adding self link employee 'singular' resource
			Link link = ControllerLinkBuilder.linkTo(EmployeeController.class).slash(employee.getEmployeeId())
					.withSelfRel();
			employee.add(link);
			voList.getEmployees().add(employee);

		}

		// Adding self link employee collection resource
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withSelfRel();

		voList.add(selfLink);

		return voList;
	}

	@RequestMapping(value = "/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
		if (id <= 3) {
			Employee employee = EmployeeDB.getEmployeeList().get(id - 1);
			// Self link
			Link selfLink = ControllerLinkBuilder.linkTo(EmployeeController.class).slash(employee.getEmployeeId())
					.withSelfRel();
			employee.add(selfLink);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}
		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

}
