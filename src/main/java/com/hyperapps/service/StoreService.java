package com.hyperapps.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.hyperapps.model.Category;

@Service
public interface StoreService {
	
	public List<Category> getStoreCategoryList(int storeId);

	


}
