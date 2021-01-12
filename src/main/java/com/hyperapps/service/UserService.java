package com.hyperapps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hyperapps.model.BusinessOperatingTimings;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;

@Service
public interface UserService {
	
	public List<User> getAllUserType();
	
	public boolean addUserType(User userType);
	
	public boolean deleteUserType(User userType);
	
	public Optional<User> getUserType(User userType);

	public boolean updateStoreRunningStatus(int storeId,int running_status);

	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst);
	
	public Store getStoreBusinessTime(int storeId,Store store);

}
