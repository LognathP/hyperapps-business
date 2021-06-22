package com.hyperapps.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.OrderDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Order;
import com.hyperapps.model.Store;
import com.hyperapps.request.OrderRequest;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	OrderDao orderDao;
	
	@Override
	public List<Order> getAllRetailerOrder(String storeId) {
		List<Order> orderList = null;
		try {
			orderList = orderDao.getAllRetailerOrders(storeId);
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE getAllRetailerOrder "+e.getMessage().toString());
			e.printStackTrace();
		}
		
		return orderList;
	}

	@Override
	public boolean updateOrderStatus(String order_id,int order_status) {
		boolean status = false;
		try {
			status = orderDao.updateOrderStatus(order_id,order_status);
		} catch (Exception e) {
			status = false;
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE cancelOrderByRetailer "+e.getMessage().toString());
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public int getOrderStatus(String order_id) {
		LOGGER.info(this.getClass(),"GET ORDER STATUS SERVICE LAYER");
		return orderDao.getOrderStatus(order_id);
	}

	
	@Override
	public Store getStoreDetails(int store_id,Store store) {
		return orderDao.getStoreDetails(store_id,store);
	}

	@Override
	public void updateOrderDetails(int order_id, String order_total, String order_grand_total, String order_details) {
		orderDao.updateOrderDetails(order_id, order_total, order_grand_total, order_details);		
	}
	
	@Override
	public void updateCancelledOrderDetails(int order_id,String order_details) {
		orderDao.updateCancelledOrderDetails(order_id,order_details);		
	}

	@Override
	public int getCustomerIdByOrderId(String order_id) {
		return orderDao.getCustomerIdByOrderId(order_id);
	}
	
	

}
