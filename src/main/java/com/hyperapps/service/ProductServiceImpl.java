package com.hyperapps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.ProductDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Category;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.Product;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ProductDao productDao;
	
	@Override
	public List<Category> getStoreCategoryList(int storeId,int branchId) {
		return productDao.getStoreCategoryList(storeId,branchId);
	}

	@Override
	public List<CategoryTree> categoryTreeFetch(int storeId, int branchId){
		return productDao.categoryTreeFetch(storeId,branchId);
	}
	
	@Override
	public List<Product> getProductsList(int storeId,int catId,int branchId) {
		return productDao.getProductsList(storeId,catId,branchId);
	}

	@Override
	public boolean updateParentCategory(int id, int active) {
		return productDao.updateParentCategory(id,active);
	}
	
	@Override
	public boolean updateRootCategory(int id, int active) {
		return productDao.updateRootCategory(id,active);
	}
	
	@Override
	public boolean updateProductStatus(int id, int active) {
		return productDao.updateProductStatus(id,active);
	}

	@Override
	public boolean updateProductData(int id, String price, String special_price, String weight, String size,
			int quantity) {
		return productDao.updateProductData(id, price,special_price,weight,size,quantity);
	}
	
}
