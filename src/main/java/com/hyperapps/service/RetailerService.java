package com.hyperapps.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hyperapps.model.Profile;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.request.ProfileUpdateRequest;

@Service
public interface RetailerService {
	
	public boolean updateStoreRunningStatus(int storeId,int running_status);

	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst);
	
	public Store getStoreBusinessTime(int storeId,Store store);
	
	public User getUserDetails(String token);

	public List<Profile> getProfileDetails(int userId);

	public boolean updateUserProfile(ProfileUpdateRequest prof, int imageUpdFlag);
	
	public boolean addTeamMember(int store_id, int user_id, String name, String mobile, int access_type, String email);

	public boolean removeTeamMember(int user_id, String email);

}
