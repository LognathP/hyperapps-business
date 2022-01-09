package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.hyperapps.model.Category;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.DeliverySettings;
import com.hyperapps.model.Designation;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Product;
import com.hyperapps.model.Store;
import com.hyperapps.model.WelcomeMessage;

@Component
public interface ProductDao {
	
	public List<Category> getStoreCategoryList(int storeId, int branchId);

	public List<CategoryTree> categoryTreeFetch(int store_id, int branchId);

	public List<Product> getProductsList(int storeId, int catId, int branchId);

	public boolean updateParentCategory(int id, int active);

	public boolean updateRootCategory(int id, int active);

	public boolean updateProductStatus(int id, int active);

	public boolean updateProductData(int id, String price, String special_price, String weight, String size,
			int quantity);
}
