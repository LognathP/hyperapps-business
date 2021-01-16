package com.hyperapps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hyperapps.model.Customer;
import com.hyperapps.model.Login;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Order;
import com.hyperapps.model.Store;
import com.hyperapps.model.User;
import com.hyperapps.request.OrderRequest;

@Service
public interface OrderService {
	
	public List<Order> getAllRetailerOrder(String storeId);

	public boolean updateOrderStatus(String order_id,int status);
	
	public int getOrderStatus(String order_id);

	public Store getStoreDetails(int store_id, Store store);

	public void updateOrderDetails(int order_id, String order_total, String order_grand_total, String order_details);

	public void updateCancelledOrderDetails(int order_id, String order_details);
	

	
}
