package com.hyperapps.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.LoginDao;
import com.hyperapps.dao.StoreDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Category;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.DeliverySettings;
import com.hyperapps.model.Designation;
import com.hyperapps.model.Login;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Product;
import com.hyperapps.model.Store;
import com.hyperapps.model.WelcomeMessage;

@Component
public class StoreServiceImpl implements StoreService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	StoreDao storeDao;
	
	@Override
	public List<WelcomeMessage> getWelcomeMessages(String token) {
		return storeDao.getWelcomeMessages(token);
	}
	
	@Override
	public boolean updateWelcomeMessage(int user_id, String message, int designation) {
		return storeDao.updateWelcomeMessage(user_id,message,designation);
	}

	@Override
	public List<Designation> getDesignation() {
		return storeDao.getDesignation();
	}
	
	@Override
	public List<DeliverySettings> getDeliverySettingsDetails(int storeId){
		return storeDao.getDeliverySettingsDetails(storeId);
	}
	
	@Override
	public boolean updateStoreRunningStatus(int storeId, int running_status) {
		return storeDao.updateStoreRunningStatus(storeId,running_status);
	}
	
	@Override
	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst) {
		return storeDao.updateTaxInfo(store_id,tax_status,tax_percentage,tax_gst);
	}

	@Override
	public Store getStoreBusinessTime(int storeId,Store store) {
		return storeDao.getStoreDetails(storeId,store);
	}
	
	@Override
	public boolean addUpdatedStandardDeliverSettingsDetails(int store_id, int delivery_type, double min_order_amount,
			double delivery_charge, double free_delivery_above, String delivery_areas, int home_delivery,int updateFlag){
		return storeDao.addUpdatedStandardDeliverSettingsDetails(store_id,delivery_type,min_order_amount,delivery_charge,
				free_delivery_above,delivery_areas,home_delivery,updateFlag);
	}
	
	@Override
	public List<OfferHistoryData> getOnGoingOfferDetails(int store_id){
		return storeDao.getOnGoingOfferDetails(store_id);
	}
	
	@Override
	public List<OfferHistoryData> getHistoryOffer(int store_id){
		return storeDao.getHistoryOffer(store_id);
	}

	@Override
	public boolean addOffer(OfferHistoryData offer){
		return storeDao.addOffer(offer);
	}
	
	@Override
	public boolean updateOffer(OfferHistoryData offer){
		return storeDao.updateOffer(offer);
	}
	
	@Override
	public boolean resumeOffer(String offer_valid, String offer_start_date, int id){
		return storeDao.resumeOffer(offer_valid,offer_start_date,id);
	}
	
	@Override
	public boolean removeOffer(int id){
		return storeDao.removeOffer(id);
	}

	@Override
	public HashMap<String, Integer> rewardShow(int storeId) {
		return storeDao.rewardShow(storeId);
	}
	
	@Override
	public boolean updateRewardPoints(int store_id, int reward_point){
		return storeDao.updateRewardPoints(store_id,reward_point);
	}
	
	
}
