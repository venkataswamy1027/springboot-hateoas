package com.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.model.User;
import com.restapi.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

}
