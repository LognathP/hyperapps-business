package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hyperapps.model.Categories;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.Child_category;
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

@Component
public interface CustomerDao {
	
	public Customer checkCustomer(Customer customer);

	public int addCustomerAddress(AddAddressRequest req);

	public boolean updateCustomerAddress(AddAddressRequest addAddrReq, int address_id);

	public boolean deleteCustomerAddress(int address_id);

	public List<CustomerAddress> getCustomerAddress(int customer_id);

	public boolean isCustomerAvailable(int customerId);

	public boolean isAddressAvailable(int addressId);

	public List<Categories> getCategories(int storeId);

	public List<Product> getProductsList(int storeId, int catId);

	public UserProfile getCustomerProfile(int customer_id);

	boolean updateCustomerProfile(UserProfile custProf);

	public Store getStoreDeliverAreas(int storeId, String store_latitude, String store_longitude);

	public List<CategoryTree> getInventoryList(int store_id);

	public List<SliderImagesData> getSliderImages(int store_id);

	public List<PromotionData> getPromotions(int store_id);

	public List<Product> searchProduct(String storied, String searchstr);

	public List<Child_category> getCategoryDetails(int store_id, int paranetCatgoryId, int subCategoryId);

	public List<OfferHistoryData> getOnGoingOfferDetails(int orderId, int customerId);

	}
