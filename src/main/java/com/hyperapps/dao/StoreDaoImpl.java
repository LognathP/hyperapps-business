package com.hyperapps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hyperapps.constants.CustomerQueryConstants;
import com.hyperapps.constants.StoreQueryConstants;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.DeliverySettings;
import com.hyperapps.model.DeliverySettings.Delivery_areas;
import com.hyperapps.model.Designation;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Store;
import com.hyperapps.model.WelcomeMessage;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.util.CommonUtils;
import com.hyperapps.util.ResponseKeys;


@Component
public class StoreDaoImpl implements StoreDao {
	
	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public List<WelcomeMessage> getWelcomeMessages(String token) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<WelcomeMessage> welList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_WELCOME_MESSAGE);
			preStmt.setString(1, token);
			res = preStmt.executeQuery();
			while(res.next()) {
				WelcomeMessage wel = new WelcomeMessage();
				wel.setId(res.getInt(1));
				wel.setMessage(res.getString(2));
				wel.setStatus(res.getInt(3));
				welList.add(wel);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getWelcomeMessages " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getWelcomeMessages " + e.getMessage());
			}

		}
		return welList;
	}
	
	@Override
	public boolean updateWelcomeMessage(int user_id, String message, int designation){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_WELCOME_MESSAGE);
			preStmt.setString(1, message);
			preStmt.setInt(2, designation);
			preStmt.setInt(3, user_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateWelcomeMessage " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateWelcomeMessage " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public List<Designation> getDesignation() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<Designation> desgnList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_DESIGNATIONS);
			res = preStmt.executeQuery();
			while(res.next()) {
				Designation des = new Designation();
				des.setId(res.getInt(1));
				des.setDesignation(res.getString(2));
				desgnList.add(des);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getDesignation " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getDesignation " + e.getMessage());
			}

		}
		return desgnList;
	}
	
	@Override
	public List<DeliverySettings> getDeliverySettingsDetails(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<DeliverySettings> destList = new ArrayList<>();
		List<Delivery_areas> dfList = new ArrayList<Delivery_areas>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_DELIVERY_SETTINGS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				DeliverySettings delv = new DeliverySettings();
				delv.setId(res.getInt(1));
				delv.setDelivery_type(res.getInt(2));
				delv.setMin_order_amount(res.getString(3));
				delv.setDelivery_charge(res.getString(4));
				delv.setFree_delivery_above(res.getString(5));
				Gson gson = new Gson(); 
				Delivery_areas[] userArray = gson.fromJson(res.getString(6), Delivery_areas[].class); 
				for(Delivery_areas df : userArray) {
					Delivery_areas deliverInfo = new Delivery_areas();
					deliverInfo.setName(df.getName());
					deliverInfo.setLat(df.getLat());
					deliverInfo.setLng(df.getLng());
					dfList.add(deliverInfo);
				}
				delv.setDelivery_areas(dfList);
				delv.setHome_delivery(res.getInt(7));
				delv.setStore_id(res.getInt(8));
				destList.add(delv);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getDeliverySettingsDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getDeliverySettingsDetails " + e.getMessage());
			}

		}
		return destList;
	}
	
	@Override
	public Store getStoreDetails(int store_id,Store store) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<DeliveryAreas> dfList = new ArrayList<DeliveryAreas>();
		List<Business_operating_timings> bsList = new ArrayList<Business_operating_timings>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_STORE_LOC_TIME_QUERY);
			preStmt.setInt(1, store_id);
			res = preStmt.executeQuery();
			while (res.next()) {
				Gson gson = new Gson(); 
				DeliveryAreas[] userArray = gson.fromJson(res.getString(1), DeliveryAreas[].class); 
				for(DeliveryAreas df : userArray) {
					DeliveryAreas deliverInfo = new DeliveryAreas();
					deliverInfo.setName(df.getName());
					deliverInfo.setLat(df.getLat());
					deliverInfo.setLng(df.getLng());
					dfList.add(deliverInfo);
				}
				gson = new Gson();
				Business_operating_timings[] userArray2 = gson.fromJson(res.getString(2), Business_operating_timings[].class); 
				for(Business_operating_timings df : userArray2) {
					Business_operating_timings bsTime = new Business_operating_timings();
					bsTime.setDay(df.getDay());
					bsTime.setFrom(df.getFrom());
					bsTime.setTo(df.getTo());
					bsList.add(bsTime);
				}
				store.setDelivery_areas(dfList);
				store.setBusiness_operating_timings(bsList);
				store.setStore_id(store_id);
				LOGGER.info(this.getClass(), "STORE DETAILS FOUND "+ store.toString());
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getStoreDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getStoreDetails " + e.getMessage());
			}

		}
		return store;
	}
	
	@Override
	public boolean updateStoreRunningStatus(int store_id,int running_status) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.STORE_RUNNING_STATUS_UPDATE);
			preStmt.setInt(1, running_status);
			preStmt.setInt(2, store_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateStoreRunningStatus " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateStoreRunningStatus " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean updateTaxInfo(int store_id, int tax_status, int tax_percentage, String tax_gst) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.STORE_TAX_INFO_UPDATE);
			preStmt.setInt(1, tax_status);
			preStmt.setInt(2, tax_percentage);
			preStmt.setString(3, tax_gst);
			preStmt.setInt(4, store_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateTaxInfo " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateTaxInfo " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean addUpdatedStandardDeliverSettingsDetails(int store_id, int delivery_type, double min_order_amount,
			double delivery_charge, double free_delivery_above, String delivery_areas, int home_delivery,int updateFlag){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		String query;
		try {
			query = StoreQueryConstants.ADD_DELIVERY_TIME_DETAILS;
			
			if(updateFlag == 1)
			query = StoreQueryConstants.UPDATE_DELIVERY_TIME_DETAILS;
			
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(query);
			preStmt.setInt(1, delivery_type);
			preStmt.setDouble(2, min_order_amount);
			preStmt.setDouble(3, delivery_charge);
			preStmt.setDouble(4, free_delivery_above);
			preStmt.setString(5, delivery_areas);
			preStmt.setInt(6, home_delivery);
			preStmt.setInt(7, store_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addUpdatedStandardDeliverSettingsDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addUpdatedStandardDeliverSettingsDetails " + e.getMessage());
			}

		}
		return updStatus;
	}
	@Override
	public List<OfferHistoryData> getOnGoingOfferDetails(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<OfferHistoryData> ohl = new ArrayList<OfferHistoryData>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_OFFER_DETAILS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while (res.next()) {
				OfferHistoryData oh = new OfferHistoryData();
				oh.setId(res.getInt(1));
				oh.setStore_id(res.getString(2));
				oh.setActive(res.getString(3));
				oh.setOffer_valid(res.getString(4));
				oh.setOffer_start_date(res.getString(5));
				oh.setOffer_type(res.getString(6));
				oh.setOffer_flat_amount(res.getString(7));
				oh.setOffer_percentage(res.getString(8));
				oh.setOffer_description(res.getString(9));  
				oh.setOffer_heading(res.getString(10));
				oh.setOffer_max_apply_count(res.getInt(11));
				oh.setOffer_percentage_max_amount(res.getString(12));
				ohl.add(oh);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getOnGoingOfferDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getOnGoingOfferDetails " + e.getMessage());
			}

		}
		return ohl;
	}
	
	@Override
	public List<OfferHistoryData> getHistoryOffer(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<OfferHistoryData> ohl = new ArrayList<OfferHistoryData>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_OLD_OFFER_DETAILS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while (res.next()) {
				OfferHistoryData oh = new OfferHistoryData();
				oh.setId(res.getInt(1));
				oh.setStore_id(res.getString(2));
				oh.setActive(res.getString(3));
				oh.setOffer_valid(res.getString(4));
				oh.setOffer_start_date(res.getString(5));
				oh.setOffer_type(res.getString(6));
				oh.setOffer_flat_amount(res.getString(7));
				oh.setOffer_percentage(res.getString(8));
				oh.setOffer_description(res.getString(9));  
				oh.setOffer_heading(res.getString(10));
				oh.setOffer_max_apply_count(res.getInt(11));
				oh.setOffer_percentage_max_amount(res.getString(12));
				ohl.add(oh);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getHistoryOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getHistoryOffer " + e.getMessage());
			}

		}
		return ohl;
	}
	
	@Override
	public boolean addOffer(OfferHistoryData offer){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_OFFER_DETAILS);
			preStmt.setString(1, offer.getOffer_heading());
			preStmt.setString(2, offer.getOffer_description());
			preStmt.setString(3, offer.getOffer_percentage());
			preStmt.setString(4, offer.getOffer_flat_amount());
			preStmt.setString(5, offer.getOffer_type());
			preStmt.setString(6, offer.getOffer_percentage_max_amount());
			preStmt.setInt(7, offer.getOffer_max_apply_count());
			preStmt.setString(8, offer.getOffer_start_date());
			preStmt.setString(9, offer.getOffer_valid());
			preStmt.setString(10, offer.getActive());
			preStmt.setString(11, offer.getStore_id());			
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean updateOffer(OfferHistoryData offer){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_OFFER_DETAILS);
			preStmt.setString(1, offer.getOffer_heading());
			preStmt.setString(2, offer.getOffer_description());
			preStmt.setString(3, offer.getOffer_percentage());
			preStmt.setString(4, offer.getOffer_flat_amount());
			preStmt.setString(5, offer.getOffer_type());
			preStmt.setString(6, offer.getOffer_percentage_max_amount());
			preStmt.setInt(7, offer.getOffer_max_apply_count());
			preStmt.setString(8, offer.getOffer_start_date());
			preStmt.setString(9, offer.getOffer_valid());
			preStmt.setString(10, offer.getActive());
			preStmt.setInt(11, offer.getId());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateOffer " + e.getMessage());
			}

		}
		return updStatus;
	}

	@Override
	public boolean resumeOffer(String offer_valid, String offer_start_date, int id){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.RESUME_OFFER);
			preStmt.setString(1, offer_start_date);
			preStmt.setString(2, offer_valid);
			preStmt.setInt(3, id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE resumeOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB resumeOffer " + e.getMessage());
			}

		}
		return updStatus;
	}

	
	@Override
	public boolean removeOffer(int id){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.DELETE_OFFER_DETAILS);
			preStmt.setInt(1, id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE removeOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB removeOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public HashMap<String, Integer> rewardShow (int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		HashMap<String, Integer> reward = new HashMap<String, Integer>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_REWARD_POINTS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				reward.put(ResponseKeys.reward_point,  res.getInt(1));
				reward.put(ResponseKeys.active,  res.getInt(2));
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE rewardShow " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON rewardShow " + e.getMessage());
			}

		}
		return reward;
	}
	
	@Override
	public boolean updateRewardPoints(int store_id, int reward_point){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_REWARD_POINTS);
			preStmt.setInt(1, reward_point);
			preStmt.setInt(2, store_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateRewardPoints " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateRewardPoints " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public String getDeviceToken(String id) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		String token = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_DEVICE_TOKEN);
			preStmt.setString(1, id);
			res = preStmt.executeQuery();
			while (res.next()) {
				token = res.getString(1);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getDeviceToken " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getDeviceToken " + e.getMessage());
			}

		}
		return token;
	}
	
	@Override
	public String getMailId(String id) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		String email = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_EMAIL_ID);
			preStmt.setString(1, id);
			res = preStmt.executeQuery();
			while (res.next()) {
				email = res.getString(1);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getMailId " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getMailId " + e.getMessage());
			}

		}
		return email;
	}

	
	@Override
	public ArrayList<String> getBusinessDeviceToken(String custId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		ArrayList<String> token = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_BUSINESS_DEVICE_TOKEN);
			preStmt.setString(1, custId);
			res = preStmt.executeQuery();
			while (res.next()) {
				token.add(res.getString(1));
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getBusinessDeviceToken " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getBusinessDeviceToken " + e.getMessage());
			}

		}
		return token;
	}

	
}
