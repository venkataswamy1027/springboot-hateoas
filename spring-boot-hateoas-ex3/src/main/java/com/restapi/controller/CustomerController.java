package com.restapi.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.exception.CustomerNotFoundException;
import com.restapi.exception.InvalidCustomerRequestException;
import com.restapi.model.Customer;
import com.restapi.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Get Customer using id. Returns HTTP 404 if Customer not found
	 * 
	 * @param CustomerId
	 * @return retrieved Customer
	 */
	@GetMapping("customer/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") Long customerId) {

		/* validate Customer Id parameter */
		if (null == customerId) {
			throw new InvalidCustomerRequestException();
		}
		Customer customer = null;
		Optional<Customer> customers = customerRepository.findById(customerId);
		if (customers.isPresent())
			customer = customers.get();

		if (null == customer) {
			throw new CustomerNotFoundException();
		}

		customer.add(linkTo(methodOn(CustomerController.class).getCustomer(customer.getCustomerId())).withSelfRel());

		customer.add(linkTo(methodOn(CustomerController.class).updateCustomer(customer, customer.getCustomerId()))
				.withRel("update"));

		customer.add(
				linkTo(methodOn(CustomerController.class).removeCustomer(customer.getCustomerId())).withRel("delete"));

		return ResponseEntity.ok(customer);
	}

	/**
	 * create Customer.
	 *
	 * @param Customer
	 */
	@PostMapping("/customer")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		customer = customerRepository.save(customer);
		return ResponseEntity.accepted().body(customer);
	}

	/**
	 * Update Customer with given Customer id.
	 *
	 * @param Customer the Customer
	 */
	@PutMapping("/customer/{customerId}")
	public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer,
			@PathVariable("customerId") Long customerId) {

		if (!customerRepository.existsById(customerId)) {
			return ResponseEntity.notFound().build();
		} else {
			customerRepository.save(customer);
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * Deletes the Customer with given Customer id if it exists and returns HTTP204.
	 *
	 * @param CustomerId the Customer id
	 */
	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<Void> removeCustomer(@PathVariable("customerId") Long customerId) {
		if (customerRepository.existsById(customerId)) {
			customerRepository.deleteById(customerId);
		}
		return ResponseEntity.noContent().build();
	}
}