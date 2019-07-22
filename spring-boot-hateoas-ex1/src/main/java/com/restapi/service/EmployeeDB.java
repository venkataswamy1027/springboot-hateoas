package com.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.restapi.model.Employee;

@Component
public class EmployeeDB {
	
	public static List<Employee> getEmployeeList() {
		List<Employee> list = new ArrayList<>();
		
		Employee empOne = new Employee(1, "Lokesh", "Gupta", "Lokesh@gmail.com");
		Employee empTwo = new Employee(2, "Amit", "Singhal", "aAmitSinghal@yahoo.com");
		Employee empThree = new Employee(3, "Kesav", "Mishra", "KesavMishra@gmail.com");

		list.add(empOne);
		list.add(empTwo);
		list.add(empThree);

		return list;
	}
}
