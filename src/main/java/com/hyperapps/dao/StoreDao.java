package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.hyperapps.model.Category;

@Component
public interface StoreDao {
	
	public List<Category> getStoreCategoryList(int storeId);

	
	
}
