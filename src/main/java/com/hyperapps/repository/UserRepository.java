package com.hyperapps.repository;

import org.springframework.data.repository.CrudRepository;

import com.hyperapps.model.User;


public interface UserRepository extends CrudRepository<User, Long>{

}
