package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hyperapps.model.Profile;
import com.hyperapps.model.User;
import com.hyperapps.request.ProfileUpdateRequest;

@Component
public interface RetailerDao {

	public boolean isStoreAvailable(int retailerId);

	public boolean updateStoreRunningStatus(int storeId, int running_status);

	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst);
	
	public User getUserDetails(String token);

	public List<Profile> getProfileDetails(int userId);

	boolean updateUserProfile(ProfileUpdateRequest prof,int userImageFlag);

	public User getParentUserDetails(int userId);	
	
	public boolean isMobileNumberExists(String mobileNum);	
	
	public boolean isEmailIdExists(String emailId);	
	
	public boolean addTeamMember(int store_id, int user_id, String name, String mobile, int access_type, String email);

	public boolean removeTeamMember(int user_id, String email);

	}