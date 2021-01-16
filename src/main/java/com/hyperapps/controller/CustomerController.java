package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.CustomerBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class CustomerController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	CustomerBusiness customerBusiness;
	
	@RequestMapping(path ="/swagger", method = RequestMethod.GET)
    public String swaggerUi() {
         return "redirect:/swagger-ui.html";
    }

	
	@GetMapping("/api/storefront/rootcategory/list")
	public Object getCategoryList() {
		Logger.info(this.getClass(),"GET CATEGORIES CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getCategoryList();
	}
	
	@GetMapping("/api/retailer/store/categoryProductTree/{store_id}/{paranetCatgoryId}/{subCategoryId}")
	public Object getCategoryDetails(@PathVariable ("store_id") int store_id,@PathVariable ("paranetCatgoryId") int paranetCatgoryId,
			@PathVariable ("subCategoryId") int subCategoryId) {
		Logger.info(this.getClass(),"GET CATEGORY LIST CUSTOMER API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getCategoryDetails(store_id,paranetCatgoryId,subCategoryId);
	}
	
	
	@GetMapping("/api/retailer/profile/nearby_store_list/{storeId}")
	public Object findNearbyStore(@PathVariable ("storeId") int storeId,@RequestParam int store_id,@RequestParam String store_latitude,
			@RequestParam String store_longitude) {
		Logger.info(this.getClass(),"FIND NEAR BY STORE API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.findNearbyStore(store_id,store_latitude,store_longitude);
	}
	
	@GetMapping("/api/retailer/store/sliderimages/{store_id}")
	public Object getSliderImages(@PathVariable ("store_id") int store_id) {
		Logger.info(this.getClass(),"GET SLIDER IMAGES API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getSliderImages(store_id);
	}
	
	@GetMapping("/api/retailer/store/promotions/{store_id}")
	public Object getPromotions(@PathVariable ("store_id") int store_id) {
		Logger.info(this.getClass(),"GET PROMOTIONS API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.getPromotions(store_id);
	}
	
	@PostMapping(value = "/api/consumer/product/search",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object searchProduct(@RequestParam String searchstr,
			@RequestParam String storied) {
		Logger.info(this.getClass(),"SEARCH PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return customerBusiness.searchProduct(searchstr,storied);
	}
	
	

	
}
