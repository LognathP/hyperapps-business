package com.hyperapps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.OrderDao;
import com.hyperapps.dao.RetailerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Customer;
import com.hyperapps.model.Profile;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.request.ProfileUpdateRequest;

@Component
public class RetailerServiceImpl implements RetailerService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	RetailerDao retailerDao;
	
	@Autowired
	OrderDao orderDao;
	
	
	@Override
	public User getUserDetails(String token) {
		return retailerDao.getUserDetails(token);
	}

	@Override
	public List<Profile> getProfileDetails(int userId) {
		return retailerDao.getProfileDetails(userId);
	}
	
	@Override
	public boolean updateUserProfile(ProfileUpdateRequest prof,int userImageFlag) {
		return retailerDao.updateUserProfile(prof,userImageFlag);
	}
	
	@Override
	public boolean addTeamMember(int store_id, int user_id, String name, String mobile, int access_type, String email) {
		return retailerDao.addTeamMember(store_id,user_id,name,mobile,access_type,email);
	}
	
	@Override
	public boolean removeTeamMember(int user_id, String email) {
		return retailerDao.removeTeamMember(user_id,email);
	}

	@Override
	public boolean addCustomer(String customers_firstname, String customers_telephone, String customers_email_address) {
		return retailerDao.addCustomer(customers_firstname,customers_telephone,customers_email_address);
	}
	
	@Override
	public boolean addfeedback(int user_id, String details){
		return retailerDao.addfeedback(user_id,details);
	}

	@Override
	public List<Customer> fetchCustomerList(int customer_type) {
		return retailerDao.fetchCustomerList(customer_type);
	}

}
