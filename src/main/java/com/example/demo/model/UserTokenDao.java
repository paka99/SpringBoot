package com.example.demo.model;

import org.springframework.data.repository.CrudRepository;

public interface UserTokenDao extends CrudRepository<UserToken, Integer> {
	UserToken findByUsername(String username);
}
