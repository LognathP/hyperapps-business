package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.ProductBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class ProductController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	ProductBusiness productBusiness;

	@GetMapping("/api/retailer/category/getStoreCategory/{storeId}/{branchId}")
	public Object getStoreCategoryList(@PathVariable ("storeId") int storeId,@PathVariable ("branchId") int branchId,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"GET STORE CATEGORY API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.getStoreCategoryList(storeId,branchId,token);
	}
	
	@GetMapping("/api/retailer/category/categorytree/{storeId}/{branchId}")
	public Object categoryTreeFetch(@PathVariable ("storeId") int storeId,@PathVariable ("branchId") int branchId,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"GET STORE CATEGORY TREE API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.categoryTreeFetch(storeId,branchId,token);
	}
	
	@GetMapping("/api/retailer/products/list/{storeId}/{categoryId}/{branchId}")
	public Object getProductList(@PathVariable("storeId") int storeId,@PathVariable("categoryId") int categoryId,@PathVariable("branchId") int branchId,
			@RequestParam String token) {
		Logger.info(this.getClass(),"GET PRODUCTS API CALL STARTED AT "+dateFormat.format(new Date()));
		Logger.info(this.getClass(),"ID's "+storeId + " " + categoryId + " "+branchId);
		return productBusiness.getProductsList(storeId,categoryId,branchId,token);
	}
	
	@PostMapping("/api/retailer/category/update/parent")
	public Object updateParentCategory(@RequestParam int id,@RequestParam int active,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"UPDATE PARENT CATEGORY STATUS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.updateParentCategory(id,active,token);
	}
	
	@PostMapping("/api/retailer/category/update/root")
	public Object updateRootCategory(@RequestParam int id,@RequestParam int active,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"UPDATE ROOT CATEGORY STATUS API CALL STARTED AT "+dateFormat.format(new Date()));
		return productBusiness.updateRootCategory(id,active,token);
	}
	
	@PostMapping("/api/retailer/products/update/status")
	public Object updateProductStatus(@RequestParam int id,@RequestParam int active,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"UPDATE ROOT CATEGORY STATUS API CALL STARTED AT "+dateFormat.format(new Date()));
		Logger.info(this.getClass(),"ID "+id + " ACTIVE "+active);
		return productBusiness.updateProductStatus(id,active,token);
	}
	
	@PostMapping("/api/retailer/products/update/data")
	public Object updateProductData(@RequestParam String token,@RequestParam int id,@RequestParam String price,@RequestParam String special_price,
			@RequestParam String weight,@RequestParam String size,@RequestParam int quantity) throws Exception {
		Logger.info(this.getClass(),"UPDATE PRODUCT DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		Logger.info(this.getClass(),"ID "+id + " PRICE "+price + " WEIGHT "+weight + " SIZE "+size + " QUA "+quantity);
		return productBusiness.updateProductData(token,id,price,special_price,weight,size,quantity);
	}
	
	
}
