package com.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import com.restapi.model.Employee;

@Service
public class EmployeeVoList extends ResourceSupport {
	
	List<Employee> employees = new ArrayList<Employee>();
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
