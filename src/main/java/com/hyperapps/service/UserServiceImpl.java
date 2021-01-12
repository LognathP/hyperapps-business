package com.hyperapps.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.OrderDao;
import com.hyperapps.dao.RetailerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.BusinessOperatingTimings;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	UserRepository userTypeRepository;
	
	@Autowired
	RetailerDao retailerDao;
	
	@Autowired
	OrderDao orderDao;
	
	
	@Override
	public boolean addUserType(User userType) {
		boolean status = false;
		try {
			userTypeRepository.save(userType);
			status = true;
		} catch (Exception e) {
			status = false;
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE ADDING/UPDATING USER TYPE  "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}


	@Override
	public boolean deleteUserType(User userType) {
		boolean status = false;
		try {
			userTypeRepository.deleteById(userType.getId());
			status = true;
		} catch (Exception e) {
			status = false;
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE DELETING USER TYPE  "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public Optional<User> getUserType(User userType) {
		Optional<User> userTypeById = null;
		try {
			userTypeById = userTypeRepository.findById(userType.getId());
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE GETTING USER TYPE  "+e.getMessage().toString());
			e.printStackTrace();
		}
		return userTypeById;
	}


	@Override
	public List<User> getAllUserType() {
		List<User> userTypeList = new ArrayList<User>();
		try {
			userTypeList =  (List<User>) userTypeRepository.findAll();
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE RETREIVING USER TYPE  "+e.getMessage().toString());
			e.printStackTrace();
		}
		return userTypeList;
		
	}


	@Override
	public boolean updateStoreRunningStatus(int storeId, int running_status) {
		return retailerDao.updateStoreRunningStatus(storeId,running_status);
	}
	
	@Override
	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst) {
		return retailerDao.updateTaxInfo(store_id,tax_status,tax_percentage,tax_gst);
	}


	@Override
	public Store getStoreBusinessTime(int storeId,Store store) {
		return orderDao.getStoreDetails(storeId,store);
	}

}
