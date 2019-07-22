package com.restapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.restapi.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}