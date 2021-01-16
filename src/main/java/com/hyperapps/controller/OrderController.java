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

	@GetMapping("/api/retailer/orders/list/{storeId}")
	public Object getOrdersRetailer(@PathVariable ("storeId") String store_id,@RequestParam String token) throws Exception {
		Logger.info(this.getClass(),"RETAILER GET ALL ORDERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.getAllRetailerOrders(store_id,token);
	}
	
	@PostMapping(path ="/api/retailer/order/process",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object processOrder(@RequestParam String token,@RequestParam int order_id,@RequestParam String order_total,
			@RequestParam String order_grand_total,@RequestParam String order_details,@RequestParam String order_items) throws Exception {
		Logger.info(this.getClass(),"PROCESS ORDER API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.processOrder(token,order_id,order_total,order_grand_total,order_details,order_items);
	}
	
	@PostMapping(path ="/api/retailer/order/cancel",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object cancelOrder(@RequestParam String token,@RequestParam int order_id,@RequestParam String order_details) throws Exception {
		Logger.info(this.getClass(),"CANCEL ORDER API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.cancelOrder(token,order_id,order_details);
	}
	
	@PostMapping(path ="/api/retailer/order/delivery",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public Object deliverOrder(@RequestParam String token,@RequestParam int order_id) throws Exception {
		Logger.info(this.getClass(),"DELIVER ORDER API CALL STARTED AT "+dateFormat.format(new Date()));
		return orderBusiness.deliverOrder(token,order_id);
	}
	
	
}
