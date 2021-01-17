package com.hyperapps.business;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Category;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.Product;
import com.hyperapps.model.Response;
import com.hyperapps.service.ProductService;
import com.hyperapps.util.ResponseKeys;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class ProductBusiness {

	@Autowired
	HyperAppsLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	@Autowired
	ProductService productService;

	@Autowired
	RetailerValidationService retailerValidationService;

	@Autowired
	APIResponse apiResponse;

	@Autowired
	Response response;

	public Object getStoreCategoryList(int storeId, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			List<Category> catList = productService.getStoreCategoryList(storeId);
			if (catList.size() != 0) {
				LOGGER.info(this.getClass(), "CATEGORIES LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Categories Listed Successfully");
				JSONObject jb1 = new JSONObject();
				jb1.put("data", catList);
				JSONObject jb = new JSONObject();
				jb.put(ResponseKeys.next_page_url, "etst");
				jb.put(ResponseKeys.category_list, jb1);
				response.setData(new JSONArray().put(jb.toMap()).toList());
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "CATEGORIES NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Categories Not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}

		}
		return respEntity;
	}

	public Object categoryTreeFetch(int storeId, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			List<CategoryTree> catList = productService.categoryTreeFetch(storeId);
			if (catList.size() != 0) {
				LOGGER.info(this.getClass(), "CATEGORY TREE LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Category Tree Listed Successfully");
				response.setData(catList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "CATEGORY TREE NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Category Tree Not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}

		}
		return respEntity;
	}

	public Object getProductsList(int storeId, int Category_id, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			List<Product> cList = productService.getProductsList(storeId, Category_id);
			if (cList.size() > 0) {
				LOGGER.info(this.getClass(), "PRODUCTS & CATEGORIES LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Products Listed Successfully");
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put(ResponseKeys.next_page_url, "etst");
				data.put(ResponseKeys.data, cList);
				response.setData(new JSONObject(data).toMap());
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);

			} else {
				LOGGER.error(this.getClass(), "NO DETAILS FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("No details found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object updateParentCategory(int id, int active, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if (productService.updateParentCategory(id, active)) {
				LOGGER.info(this.getClass(), "PARENT CATEGORY STATUS UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Parent Category Status Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE PARENT CATEGOTY STATUS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to Update Parent Category Status");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object updateRootCategory(int id, int active, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if (productService.updateRootCategory(id, active)) {
				LOGGER.info(this.getClass(), "ROOT CATEGORY STATUS UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Root Category Status Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE ROOT CATEGOTY STATUS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to Update Root Category Status");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object updateProductStatus(int id, int active, String token) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if (productService.updateProductStatus(id, active)) {
				LOGGER.info(this.getClass(), "PRODUCT STATUS UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Product Status Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE PRODUCT STATUS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to Update Product Status");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object updateProductData(String token, int id, String price, String special_price, String weight,
			String size, int quantity) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if (productService.updateProductData(id, price,special_price,weight,size,quantity)) {
				LOGGER.info(this.getClass(), "PRODUCT DETAILS UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Product Details Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "UNABLE TO UPDATE PRODUCT DETAILS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Unable to Update Product Details");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
			}
		}
		return respEntity;
	}

}
