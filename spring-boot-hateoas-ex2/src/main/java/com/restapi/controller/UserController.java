package com.restapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

import com.restapi.exception.UserNotFoundException;
import com.restapi.model.User;
import com.restapi.service.UserService;

import java.util.List;
import java.util.Optional;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping(value = "/user/{id}", produces = { "application/hal+json" })
	public Resource<User> getUser(@PathVariable(value = "id", required = true) Long id) {
		logger.info("Entering into getUser method {}", System.currentTimeMillis());
		User user = userService.getUserById(id).orElse(null);
		if (user == null) {
			throw new UserNotFoundException("User not found with id - " + id);
		}
		user.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel().withType("GET"));
		return new Resource<>(user);
	}

	@GetMapping(value = "/users", produces = { "application/hal+json" })
	public Resources<User> getUsers() {
		logger.info("Entering into getUsers method {}", System.currentTimeMillis());
		List<User> users = userService.getAllUsers();
		for (User user : users) {
			Long userId = user.getUserId();
			user.add(linkTo(methodOn(UserController.class).getUser(userId)).withRel("get_user").withType("GET"));
			user.add(linkTo(methodOn(UserController.class).deleteUser(userId)).withRel("delete_user")
					.withType("DELETE"));
		}
		Link link = linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel().withType("GET");
		return new Resources<>(users, link);
	}

	@GetMapping(value = "/getAllUsers", produces = { "application/hal+json" })
	public Resources<User> getAllUsers() {
		logger.info("Entering into getUsers method {}", System.currentTimeMillis());
		List<User> users = userService.getAllUsers();
		Link link = linkTo(methodOn(UserController.class).getUsers()).withSelfRel().withType("GET");
		return new Resources<>(users, link);
	}

	@PostMapping(value = "/user", produces = { "application/hal+json" })
	public Resource<User> addUser(@RequestBody User user) {
		logger.info("Entering into addUser method {}", System.currentTimeMillis());
		user = userService.saveUser(user);
		Link link = linkTo(methodOn(UserController.class).addUser(user)).withSelfRel().withType("POST");
		return new Resource<>(user, link);
	}

	@DeleteMapping(value = "/user/{id}", produces = { "application/hal+json" })
	public Resource<User> deleteUser(@PathVariable(value = "id", required = true) Long id) {
		logger.info("Entering into deleteUser method {}", System.currentTimeMillis());
		Optional<User> user = userService.getUserById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found with id - " + id);
		}
		userService.deleteUserById(id);
		User userNull = new User();
		userNull.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("get_users").withType("GET"));
		return new Resource<>(userNull);
	}
}