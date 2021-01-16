package com.hyperapps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperapps.dao.CustomerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Categories;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.CategoryTree.Child_category;
import com.hyperapps.model.Customer;
import com.hyperapps.model.CustomerAddress;
import com.hyperapps.model.UserProfile;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Product;
import com.hyperapps.model.PromotionData;
import com.hyperapps.model.SliderImagesData;
import com.hyperapps.model.Store;
import com.hyperapps.repository.CustomerRepository;
import com.hyperapps.request.AddAddressRequest;

@Component
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Customer checkCustomer(Customer customer) {
		try {
			customer = customerDao.checkCustomer(customer);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE checkUser "+e.getMessage().toString());
			e.printStackTrace();
		}
		return customer;
	}


	@Override
	public Customer addCustomer(Customer customer) {
		try {
			customer = customerRepository.save(customer);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE ADDING/UPDATING CUSTOMER "+e.getMessage().toString());
			e.printStackTrace();
		}
		return customer;
	}


	@Override
	public int addCustomerAddress(AddAddressRequest req) {
		int i = 0;
		try {
			ObjectMapper Obj = new ObjectMapper();
			System.out.println(Obj.writeValueAsString(req));
			i = customerDao.addCustomerAddress(req);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE addCustomerAddress "+e.getMessage().toString());
			e.printStackTrace();
		}
		return i;
	}


	@Override
	public boolean updateCustomerAddress(AddAddressRequest addAddrReq, int address_id) {
		boolean status = false;
		try {
			status = customerDao.updateCustomerAddress(addAddrReq,address_id);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE updateCustomerAddress "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public boolean deleteCustomerAddress(int address_id) {
		boolean status = false;
		try {
			status = customerDao.deleteCustomerAddress(address_id);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE deleteCustomerAddress "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public List<CustomerAddress> getCustomerAddress(int customer_id) {
		return customerDao.getCustomerAddress(customer_id);
	}


	@Override
	public List<Categories> getCategories(int storeId) {
		return customerDao.getCategories(storeId);
	}
	
	@Override
	public UserProfile getCustomerProfile(int customer_id) {
		return customerDao.getCustomerProfile(customer_id);
	}
	
	@Override
	public boolean updateCustomerProfile(UserProfile cpf) {
			return customerDao.updateCustomerProfile(cpf);
	}
	
	@Override
	public Store getStoreDeliverAreas(int storeId,String store_latitude, String store_longitude) {
		return customerDao.getStoreDeliverAreas(storeId,store_latitude,store_longitude);
	}
	
	@Override
	public List<SliderImagesData> getSliderImages(int store_id){
		return customerDao.getSliderImages(store_id);
	}
	
	@Override
	public List<PromotionData> getPromotions(int store_id){
		return customerDao.getPromotions(store_id);
	}

	@Override
	public List<Product> searchProduct(String storied, String searchstr){
		return customerDao.searchProduct(storied,searchstr);
	}

	@Override
	public List<Child_category> getCategoryDetails(int store_id, int paranetCatgoryId, int subCategoryId){
		return customerDao.getCategoryDetails(store_id,paranetCatgoryId,subCategoryId);
	}
	
	

	

	

}
