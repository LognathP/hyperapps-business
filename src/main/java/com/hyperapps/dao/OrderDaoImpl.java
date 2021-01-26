package com.hyperapps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.constants.LoginQueryConstants;
import com.hyperapps.constants.OrderQueryConstants;
import com.hyperapps.constants.StoreQueryConstants;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.CustomerInfo;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.DeliveryInfo;
import com.hyperapps.model.Login;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Order;
import com.hyperapps.model.OrderItems;
import com.hyperapps.model.PaymentResponse;
import com.hyperapps.model.Product;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Profile.Business_phone;
import com.hyperapps.model.Store;
import com.hyperapps.request.OrderItemsRequest;
import com.hyperapps.request.OrderRequest;
import com.hyperapps.util.CommonUtils;


@Component
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public List<Order> getAllRetailerOrders(String storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt2 = null;
		ResultSet res2 = null;
		ResultSet res3 = null;
		PaymentResponse pay = new PaymentResponse();
		List<Order> orderList = new ArrayList<Order>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.GET_RETAILER_ORDERS);
			preStmt.setString(1, storeId);
			res = preStmt.executeQuery();
			while (res.next()) {
				Order order = new Order();
				order.setOrder_id(res.getInt(1));
				order.setOrder_message(res.getString(2));
				order.setOrder_placed_date(res.getString(3));
				order.setOrder_updated_at(res.getString(4));
				order.setCustomer_id(res.getInt(5));
				order.setOrder_status(res.getInt(6));
				order.setRetailer_id(res.getInt(7));
				order.setOrder_total(res.getString(8));
				order.setOrder_grand_total(res.getString(9));
				order.setPayment_details(res.getString(10));
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setCustomer_name(res.getString(11));
				customerInfo.setCustomers_email_address(res.getString(12));
				customerInfo.setCustomers_telephone(res.getString(13));
				customerInfo.setStreet_address(res.getString(14));
				customerInfo.setPostcode(res.getInt(15));
				customerInfo.setCity(res.getString(16));
				customerInfo.setState(res.getString(17));
				customerInfo.setCountry(res.getString(18));
				order.setCustomer_info(customerInfo);
				Store store = new Store();
				store.setBusiness_name(res.getString(19));
				store.setPhysical_store_address(res.getString(20));
				List<Business_phone> bpList = new ArrayList<Business_phone>();
				Gson gson = new Gson(); 
				Business_phone[] userArray = gson.fromJson(res.getString(21), Business_phone[].class); 
				for(Business_phone bp : userArray) {
					Business_phone bPhone = new Business_phone();
					bPhone.setPhone(bp.getPhone());	
					bpList.add(bPhone);
				}
				store.setBusiness_phone(bpList);
				store.setUser_image(res.getString(22));
				List<OrderItems> odList = new ArrayList<OrderItems>();
				preStmt2 = connection.prepareStatement(OrderQueryConstants.GET_ORDER_ITEMS_BYID);
				preStmt2.setInt(1, order.getOrder_id());
				res2 = preStmt2.executeQuery();
				while (res2.next()) {
					OrderItems ot = new OrderItems();
					ot.setOrder_item_id(res2.getInt(1));
					ot.setOrder_item_quantity(res2.getInt(2));
					ot.setPrice_per_unit(res2.getString(3));
					ot.setItem_status(res2.getInt(4));
					ot.setTotal(res2.getString(5));
					Product p = new Product();
					p.setName(res2.getString(6));
					p.setImage_path(res2.getString(7));
					ot.setProduct_info(p);
					odList.add(ot);				
				}
				res2.close();
				preStmt2.close();
				order.setOrder_items(odList);
				preStmt2 = connection.prepareStatement(OrderQueryConstants.GET_OFFER_DETAILS_BYID);
				preStmt2.setInt(1, order.getOrder_id());
				res3 = preStmt2.executeQuery();
				OfferHistoryData offer = new OfferHistoryData();
				while (res3.next()) {
				offer.setId(res3.getInt(1));
				offer.setOffer_heading(res3.getString(2));
				offer.setOffer_description(res3.getString(3));
				offer.setOffer_percentage(res3.getString(4));
				offer.setOffer_flat_amount(res3.getString(5));
				offer.setOffer_type(res3.getString(6));
				offer.setOffer_start_date(res3.getString(7));
				offer.setOffer_valid(res3.getString(8));
				offer.setActive(res3.getString(9));
				offer.setStore_id(res3.getString(10));
				order.setOffer_details(offer);	
				}
				res3.close();
				preStmt2.close();
				orderList.add(order);
				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getAllRetailerOrders " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getAllRetailerOrders " + e.getMessage());
			}

		}
		return orderList;
	}

	@Override
	public boolean updateOrderStatus(String order_id,int order_status) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = true;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.UPDATE_ORDER_STATUS);
			preStmt.setInt(1, order_status);
			preStmt.setString(2, order_id);
			preStmt.executeUpdate();
			
		} catch (Exception e) {
			updStatus = false;
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isNewUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB isNewUser " + e.getMessage());
			}

		}
		return updStatus;
	}

	@Override
	public int getOrderStatus(String orderId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		int status = 0;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.GET_ORDER_STATUS);
			preStmt.setString(1, orderId);
			res = preStmt.executeQuery();
			while (res.next()) {
				status = res.getInt(1);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getOrderStatus " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getOrderStatus " + e.getMessage());
			}

		}
		return status;
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
	public void updateOrderDetails(int order_id, String order_total, String order_grand_total, String order_details) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.UPDATE_ORDER_DETAILS);
			preStmt.setString(1, order_details);
			preStmt.setString(2, order_total);
			preStmt.setString(3, order_grand_total);
			preStmt.setInt(4, order_id);
			preStmt.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateOrderDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateOrderDetails " + e.getMessage());
			}

		}
	}
	
	@Override
	public void updateCancelledOrderDetails(int order_id,String order_details) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.UPDATE_CANCELLED_ORDER_DETAILS);
			preStmt.setString(1, order_details);
			preStmt.setInt(2, order_id);
			preStmt.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateCancelledOrderDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateCancelledOrderDetails " + e.getMessage());
			}

		}
	}
		
}
