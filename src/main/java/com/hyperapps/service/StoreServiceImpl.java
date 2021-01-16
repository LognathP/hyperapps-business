package com.hyperapps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.LoginDao;
import com.hyperapps.dao.StoreDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Category;
import com.hyperapps.model.Login;

@Component
public class StoreServiceImpl implements StoreService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	StoreDao storeDao;
	
	@Override
	public List<Category> getStoreCategoryList(int storeId) {
		return storeDao.getStoreCategoryList(storeId);
	}

	
	

}
