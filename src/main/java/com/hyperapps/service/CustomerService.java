package com.hyperapps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hyperapps.model.Categories;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.CategoryTree.Child_category;
import com.hyperapps.model.Customer;
import com.hyperapps.model.CustomerAddress;
import com.hyperapps.model.UserProfile;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.Login;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Product;
import com.hyperapps.model.PromotionData;
import com.hyperapps.model.SliderImagesData;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.request.AddAddressRequest;

@Service
public interface CustomerService {
	
	public Customer checkCustomer(Customer customer);
	
	public Customer addCustomer(Customer customer);
	
	public int addCustomerAddress(AddAddressRequest req);

	public boolean updateCustomerAddress(AddAddressRequest addAddrReq, int address_id);

	public boolean deleteCustomerAddress(int address_id);

	public List<CustomerAddress> getCustomerAddress(int customer_id);

	public List<Categories> getCategories(int storeId);

	public UserProfile getCustomerProfile(int customer_id);

	boolean updateCustomerProfile(UserProfile cpf);

	public Store getStoreDeliverAreas(int store_id, String store_latitude, String store_longitude);

	public List<SliderImagesData> getSliderImages(int store_id);

	public List<PromotionData> getPromotions(int store_id);

	public List<Product> searchProduct(String storied, String searchstr);

	public List<Child_category> getCategoryDetails(int store_id, int paranetCatgoryId, int subCategoryId);


	
}
