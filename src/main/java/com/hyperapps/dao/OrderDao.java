package com.hyperapps.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hyperapps.model.Login;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Order;
import com.hyperapps.model.Store;
import com.hyperapps.request.OrderRequest;

@Component
public interface OrderDao {
	
	public List<Order> getAllRetailerOrders(String storeId);

	public List<Order> getAllCustomerOrders(String customerId);

	public boolean updateOrderStatus(String order_id,int status);
	
	public int getOrderStatus(String orderId);

	public Store getStoreDetails(int store_id, Store store);

	public boolean placeOrder(OrderRequest orderReq);
	
	}
