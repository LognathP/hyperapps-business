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
import com.hyperapps.model.Child_category;
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
import com.hyperapps.validation.CustomerValidationService;
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
	CustomerValidationService customerValidationService;
	
	@Autowired
	RetailerValidationService retailerValidationService;
	
	@SuppressWarnings("unchecked")
	public Object addAddress(String customer_id, String address_label, String door_no, String street_name,
			String city_name, String pin_code, String state, String country, double address_latitude,
			double address_longitude)  {
		int addrId = 0;
		JSONObject js = new JSONObject();
		AddAddressRequest addAddrReq = new AddAddressRequest();
		addAddrReq.setCustomer_id(Integer.valueOf(customer_id));
		addAddrReq.setAddress_label(address_label);
		addAddrReq.setDoor_no(door_no);
		addAddrReq.setStreet_name(street_name);
		addAddrReq.setCity_name(city_name);
		addAddrReq.setState(state);
		addAddrReq.setPin_code(pin_code);
		addAddrReq.setCountry(country);
		addAddrReq.setAddress_latitude(String.valueOf(address_latitude));
		addAddrReq.setAddress_longitude(String.valueOf(address_longitude));
		ObjectMapper Obj = new ObjectMapper();
		try {
			System.out.println(Obj.writeValueAsString(addAddrReq));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((addrId = customerService.addCustomerAddress(addAddrReq)) > 0) {
			LOGGER.info(this.getClass(), "ADDRESS ADDED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Address Added Successfully");
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			js.put("address_id", addrId);
			response.setData(js);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "ADDRESS ADDITION FAILED");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			response.setMessage("Error occured in Adding Address, Please try Again");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}

	public Object updateAddress(String customer_id, String address_label, String door_no, String street_name,
			String city_name, String pin_code, String state, String country, double address_latitude,
			double address_longitude, int address_id) {
		ResponseEntity<?> respEntity = null;
		AddAddressRequest addAddrReq = new AddAddressRequest();
		addAddrReq.setCustomer_id(Integer.valueOf(customer_id));
		addAddrReq.setAddress_label(address_label);
		addAddrReq.setDoor_no(door_no);
		addAddrReq.setStreet_name(street_name);
		addAddrReq.setCity_name(city_name);
		addAddrReq.setState(state);
		addAddrReq.setPin_code(pin_code);
		addAddrReq.setCountry(country);
		addAddrReq.setAddress_latitude(String.valueOf(address_latitude));
		addAddrReq.setAddress_longitude(String.valueOf(address_longitude));
		ObjectMapper Obj = new ObjectMapper();
		try {
			System.out.println(Obj.writeValueAsString(addAddrReq));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((respEntity = customerValidationService.validateAddressId(address_id, respEntity)) == null) {
			if (customerService.updateCustomerAddress(addAddrReq, address_id)) {
				LOGGER.info(this.getClass(), "ADDRESS UPDATED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Address Updated Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "ADDRESS UPDATION FAILED");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Error occured in Updating Address, Please try Again");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
		}
		return respEntity;

	}

	public Object deleteAddress(int address_id) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = customerValidationService.validateAddressId(address_id, respEntity)) == null) {
			if (customerService.deleteCustomerAddress(address_id)) {
				LOGGER.info(this.getClass(), "ADDRESS DELETED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Address Deleted Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "ADDRESS DELETION FAILED");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Error occured in Deleting Address, Please try Again");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
		}
		return respEntity;
	}

	public Object getAddress(int customer_id) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = customerValidationService.validateCustomerId(customer_id, respEntity)) == null) {
			List<CustomerAddress> cList = customerService.getCustomerAddress(customer_id);
			if (cList.size() > 0) {
				LOGGER.info(this.getClass(), "CUSTOMER ADDRESS LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Customer Address Listed Successfully");
				response.setData(cList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "NO CUSTOMER ADDRESS NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("No Customer Address found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
		}
		return respEntity;

	}

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
	public Object getProductsList(int storeId, int Category_id) {
		List<Product> cList = customerService.getProductsList(storeId,Category_id);
		if (cList.size() > 0) {
			LOGGER.info(this.getClass(), "PRODUCTS & CATEGORIES LISTED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Products Listed Successfully");
			JSONObject js = new JSONObject();
			js.put("products", cList);
			response.setData(js);
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO DETAILS FOUND");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("No details found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}

	public Object getCustomerProfile(int customer_id) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = customerValidationService.validateCustomerId(customer_id, respEntity)) == null) {	
		UserProfile cPf = customerService.getCustomerProfile(customer_id);
		if (cPf != null) {
			LOGGER.info(this.getClass(), "CUSTOMER PROFILE RETRIEVED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Customer Profile retreived Successfully");
			response.setData(cPf);
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO CUSTOMER DETAILS FOUND");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("No Customer details found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
	}
		return respEntity;
	}
	
	public Object updateCustomerProfile(int id,String customers_gender, String customers_firstname, String customers_lastname,
			String customers_dob, String customers_email_address, String customers_default_address_id,
			String customers_telephone, String customers_fax, String customers_password,
			String customers_newsletter) {
		UserProfile cpf = new UserProfile();
		cpf.setCustomers_gender(customers_gender);
		cpf.setCustomers_firstname(customers_firstname);
		cpf.setCustomers_lastname(customers_lastname);
		cpf.setCustomers_dob(customers_dob);
		cpf.setCustomers_email_address(customers_email_address);
		cpf.setCustomers_default_address_id(CommonUtils.emptyIntToZero(customers_default_address_id));
		cpf.setCustomers_telephone(customers_telephone);
		cpf.setCustomers_fax(customers_fax);
		cpf.setCustomers_password(customers_password);
		cpf.setCustomers_newsletter(CommonUtils.emptyIntToZero(customers_newsletter));	
		cpf.setId(id);
		if (customerService.updateCustomerProfile(cpf)) {
			LOGGER.info(this.getClass(), "CUSTOMER PROFILE UPDATED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Customer Profile Updated Successfully");
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO UPDATE CUSTOMER DETAILS");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			response.setMessage("Unable to updated Customer Details");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
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

	public Object getInventoryList(int store_id) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<CategoryTree> categoryList = new ArrayList<CategoryTree>();
			categoryList = customerService.getInventoryList(store_id);
			if (categoryList.size()>0) {
				LOGGER.info(this.getClass(), "INVENTORY LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Inventory Listed Successfully");
				response.setData(categoryList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "INVENTORY LIST NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Inventory List not found");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			}
		return respEntity;
	}

	public Object getSliderImages(int store_id) {
		ResponseEntity<?> respEntity = null;
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
		ResponseEntity<?> respEntity = null;
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
		ResponseEntity<?> respEntity = null;
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
	
	public Object getOngoingOffer(int store_id,int customer_Id,String token) {
		ResponseEntity<?> respEntity = null;
		if ((respEntity = retailerValidationService.validateStoreId(store_id, respEntity)) == null) {
			List<OfferHistoryData> offerList = new ArrayList<OfferHistoryData>();
			offerList = customerService.getOnGoingOfferDetails(store_id, customer_Id);
			if (offerList.size()>0) {
				LOGGER.info(this.getClass(), "ON GOING OFFERS LISTED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("On Going Offers Listed Successfully");
				response.setData(offerList);
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			} else {
				LOGGER.error(this.getClass(), "ON GOING OFFERS LIST NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("On Going Offers not found");
				response.setData(null);
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			}
		return respEntity;
	}
}
