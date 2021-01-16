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
public interface StoreDao {
	
	public List<Category> getStoreCategoryList(int storeId);

	public List<WelcomeMessage> getWelcomeMessages(String token);

	public boolean updateWelcomeMessage(int user_id, String message, int designation);

	public List<Designation> getDesignation();

	public List<DeliverySettings> getDeliverySettingsDetails(int storeId);

	public boolean updateStoreRunningStatus(int storeId, int running_status);

	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst);
	
	public Store getStoreDetails(int store_id, Store store);

	public boolean addUpdatedStandardDeliverSettingsDetails(int store_id, int delivery_type, double min_order_amount,
			double delivery_charge, double free_delivery_above, String delivery_areas, int home_delivery,int updateFlag);
	
	public List<CategoryTree> categoryTreeFetch(int store_id);

	public List<OfferHistoryData> getOnGoingOfferDetails(int store_id);

	public List<OfferHistoryData> getHistoryOffer(int store_id);

	public boolean addOffer(OfferHistoryData offer);
	
	public boolean updateOffer(OfferHistoryData offer);

	public boolean resumeOffer(String offer_valid, String offer_start_date, int id);

	public boolean removeOffer(int id);
	
	public List<Product> getProductsList(int storeId, int catId);
}
