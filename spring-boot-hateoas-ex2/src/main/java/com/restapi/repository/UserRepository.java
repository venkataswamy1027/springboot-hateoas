package com.restapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.restapi.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
