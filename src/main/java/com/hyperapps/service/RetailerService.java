package com.hyperapps.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hyperapps.model.Customer;
import com.hyperapps.model.Profile;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.request.ProfileUpdateRequest;

@Service
public interface RetailerService {
	
	public List<User> getUserDetails(String token);

	public List<Profile> getProfileDetails(int userId);

	public boolean updateUserProfile(ProfileUpdateRequest prof, int imageUpdFlag);
	
	public boolean addTeamMember(int store_id, int user_id, String name, String mobile, int access_type, String email);

	public boolean removeTeamMember(int user_id, String email);

	public boolean addCustomer(String customers_firstname, String customers_telephone, String customers_email_address);

	public boolean addfeedback(int user_id, String details);

	public List<Customer> fetchCustomerList(String customer_type);

}
