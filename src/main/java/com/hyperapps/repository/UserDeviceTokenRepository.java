package com.hyperapps.repository;

import org.springframework.data.repository.CrudRepository;

import com.hyperapps.model.User;
import com.hyperapps.model.UserDeviceToken;


public interface UserDeviceTokenRepository extends CrudRepository<UserDeviceToken, Long>{

}
