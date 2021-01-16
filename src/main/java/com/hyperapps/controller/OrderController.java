package com.hyperapps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyperapps.business.OrderBusiness;
import com.hyperapps.logger.HyperAppsLogger;

@RestController
public class OrderController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	HyperAppsLogger Logger;
	
	@Autowired
	OrderBusiness orderBusiness;

	@PostMapping("/api/retailer/orders/list")
	public Object getOrdersRetailer(@RequestParam String store_id) throws Exception {
		Logger.info(this.getClass(),"RETAILER GET ALL ORDERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.getAllRetailerOrders(store_id);
	}
	
	@GetMapping("/api/retailer/order/orderlist/{customerId}")
	public Object getOrdersCustomer(@PathVariable ("customerId") String customerId) throws Exception {
		Logger.info(this.getClass(),"CUSTOMER GET ALL ORDERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.getAllCustomerOrders(customerId);
	}

	@PostMapping(path ="/api/retailer/order/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object customerPlaceOrder(@RequestParam int store_id,@RequestParam int customer_id,
			@RequestParam double order_total,@RequestParam double order_grand_total,
			@RequestParam String order_details,@RequestParam String order_items,
			@RequestParam String location,@RequestParam int offer_id,@RequestParam String payment_details) throws Exception {
		Logger.info(this.getClass(),"CUSTOMER PLACE ORDER API CALL STARTED AT "+dateFormat.format(new Date()));
		Logger.info(this.getClass(),"REQUEST DETAILS IN CONTROLLER=== STORE ID"+store_id+" CUSTOMER ID "+customer_id);
		return orderBusiness.placeOrder(store_id,customer_id,order_total,order_grand_total,order_details,
				order_items,location,offer_id,payment_details);
	}

	@PostMapping(path ="/api/retailer/order/customer/approval",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object updateOrderStatus(@RequestParam int order_id,@RequestParam int status) throws Exception {
		Logger.info(this.getClass(),"UPDATE ORDER API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.updateOrderStatus(String.valueOf(order_id),status);
	}
	
	
}
