package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hyperapps.model.Customer;
import com.hyperapps.model.Product;
import com.hyperapps.model.Profile;
import com.hyperapps.model.User;
import com.hyperapps.request.ProfileUpdateRequest;

@Component
public interface RetailerDao {

	public boolean isStoreAvailable(int retailerId);

	public User getUserDetails(String token);

	public List<Profile> getProfileDetails(int userId);

	boolean updateUserProfile(ProfileUpdateRequest prof,int userImageFlag);

	public User getParentUserDetails(int userId);	
	
	public boolean isMobileNumberExists(String mobileNum);	
	
	public boolean isEmailIdExists(String emailId);	
	
	public boolean addTeamMember(int store_id, int user_id, String name, String mobile, int access_type, String email);

	public boolean removeTeamMember(int user_id, String email);

	public boolean addCustomer(String customers_firstname, String customers_telephone, String customers_email_address);

	public boolean addfeedback(int user_id, String details);

	public List<Customer> fetchCustomerList(int customer_type);
	
	}
