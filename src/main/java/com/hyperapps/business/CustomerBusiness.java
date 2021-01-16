package com.hyperapps.business;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Categories;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.CategoryTree.Child_category;
import com.hyperapps.model.CustomerAddress;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.UserProfile;
import com.hyperapps.model.Product;
import com.hyperapps.model.PromotionData;
import com.hyperapps.model.Response;
import com.hyperapps.model.SliderImagesData;
import com.hyperapps.model.Store;
import com.hyperapps.request.AddAddressRequest;
import com.hyperapps.service.CustomerService;
import com.hyperapps.util.CommonUtils;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class CustomerBusiness {


	
	@Autowired
	HyperAppsLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	
	@Autowired
	CustomerService customerService;

	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;


	@Autowired
	RetailerValidationService retailerValidationService;
	
	
	@SuppressWarnings("unchecked")
	public Object getCategoryList() {
		
		List<Categories> cList = customerService.getCategories(Integer.parseInt(configProp.getConfigValue("default.storeid")));
		if (cList.size() > 0) {
			LOGGER.info(this.getClass(), "CATEGORIES LISTED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Categories Listed Successfully");
			JSONObject js = new JSONObject();
			js.put("data", cList);
			response.setData(js);
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO CATEGORIES FOUND");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("No Categories found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}

	

	

	@SuppressWarnings("unchecked")
	public Object findNearbyStore(int store_id, String store_latitude, String store_longitude) {
		store_latitude = "0.0"; //Added for Testing
		store_longitude = "0.0"; //Added for Testing
		Store store = customerService.getStoreDeliverAreas(store_id,store_latitude,store_longitude);
		LOGGER.info(this.getClass(),"STORE DETAILS IN FIND NEARBY "+store.toString());
		if(store.getStore_id()!=0)
		{
					LOGGER.error(this.getClass(),"STORE LOCATION AVAILABLITY FOUND");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("Store found !!");
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					JSONObject js = new JSONObject();
					js.put("store", store);
					response.setData(js);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);					
		}
		else
		{
			LOGGER.error(this.getClass(),"STORE LOCATION NOT FOUND");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("Store Location details not Found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}



	public Object getSliderImages(int store_id) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<SliderImagesData> sliderImageList = new ArrayList<SliderImagesData>();
			sliderImageList = customerService.getSliderImages(store_id);
			if (sliderImageList.size()>0) {
				
				LOGGER.info(this.getClass(), "SLIDER IMAGES LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Slider Images Listed Successfully");
				response.setData(sliderImageList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "SLIDER IMAGES LIST NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Slider Images not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			}
		return respEntity;
	}

	public Object getPromotions(int store_id) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<PromotionData> promoList = new ArrayList<PromotionData>();
			promoList = customerService.getPromotions(store_id);
			if (promoList.size()>0) {
				
				LOGGER.info(this.getClass(), "PROMOTIONS LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Promotions Listed Successfully");
				response.setData(promoList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "PROMOTIONS LIST NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Promotions not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			}
		return respEntity;
	}

	public Object searchProduct(String searchstr, String storied) {
		List<Product> products = new ArrayList<Product>();
		products = customerService.searchProduct(storied,searchstr);
		if (products.size()>0) {
			
			LOGGER.info(this.getClass(), "SEARCH PRODUCTS LISTED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Search Products Listed Successfully");
			response.setData(products);
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);		
		} else {
			LOGGER.error(this.getClass(), "SEARCH PRODUCTS LIST NOT FOUND");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("Search Products not found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}

	public Object getCategoryDetails(int store_id, int paranetCatgoryId, int subCategoryId) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<Child_category> childCategoryList = new ArrayList<Child_category>();
			childCategoryList = customerService.getCategoryDetails(store_id,paranetCatgoryId,subCategoryId);
			if (childCategoryList.size()>0) {
				LOGGER.info(this.getClass(), "CHILD CATEGORY LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Child Category Listed Successfully");
				response.setData(childCategoryList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "CHILD CATEGORY LIST NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Child Category List not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			}
		return respEntity;
		}
	
	
}
