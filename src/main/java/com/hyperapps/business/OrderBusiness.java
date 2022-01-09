package com.hyperapps.business;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.fcm.PushNotificationService;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.Order;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Response;
import com.hyperapps.model.Store;
import com.hyperapps.request.OrderItemsRequest;
import com.hyperapps.request.OrderLocationRequest;
import com.hyperapps.request.OrderRequest;
import com.hyperapps.service.OrderService;
import com.hyperapps.service.StoreService;
import com.hyperapps.util.CalendarUtil;
import com.hyperapps.util.CommonUtils;
import com.hyperapps.util.ResponseKeys;
import com.hyperapps.validation.OrderValidationService;
import com.hyperapps.validation.RetailerValidationService;

@Component
public class OrderBusiness {

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	ConfigProperties configProp;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	RetailerValidationService retailerValidationService;
	
	@Autowired
	OrderValidationService orderValidationService;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	PushNotificationService pushNotificationService;
	
	public Object getAllRetailerOrders(String storeId,String token)
	{
		LOGGER.info(this.getClass(),"ALL RETAILER ORDER BUSINESS LAYER");
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if ((respEntity = retailerValidationService.validateStoreId(Integer.parseInt(storeId), respEntity)) == null) {
				List<Order> orderList = new ArrayList<Order>();
				orderList = orderService.getAllRetailerOrder(storeId);
				if (orderList.size() > 0) {
					LOGGER.info(this.getClass(),"ORDER LISTED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("Orders listed Successfully");
					HashMap<String,Object> orders = new HashMap<String,Object>();
					orders.put(ResponseKeys.orders, orderList);
					JSONObject jb = new JSONObject(orders);
					response.setData(jb);
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				} else {
					LOGGER.error(this.getClass(),"ORDER LIST FAILED");
					response.setStatus(HttpStatus.NOT_FOUND.toString());
					response.setMessage("No Orders Found");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
				}
			}
		}
		return respEntity;
		
	}
	
	public Object cancelOrder(String order_id,int order_status) {
		LOGGER.info(this.getClass(),"RETAILER CANCEL ORDER BUSINESS LAYER");
		if(orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_INITIATED || 
				orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_PROCESSED)
		{
			if(orderService.updateOrderStatus(order_id,order_status))
			{
				sendNotificationsUpdateOrder(orderService.getCustomerIdByOrderId(order_id), order_status);
				LOGGER.info(this.getClass(),"ORDER CANCELLED SUCCESSFULLY");
				response.setStatus(HttpStatus.OK.toString());
				response.setMessage("Order Cancelled Successfully");
				response.setError(HyperAppsConstants.RESPONSE_FALSE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);			}
			else
			{
				LOGGER.error(this.getClass(),"ORDER CANCELLATION FAILED");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cancellation Failed");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		}
		else
		{
			LOGGER.error(this.getClass(),"ORDER CANCELLATION FAILED");
			response.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
			response.setMessage("Order cannot be Cancelled");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		}
		
		
	}

	
	public Object updateOrderStatus(String order_id, int orderAccepted) {
		if(orderAccepted == 0)
		orderAccepted = HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER;
		
		String message;
			LOGGER.info(this.getClass(),"RETAILER ACCEPT ORDER BUSINESS LAYER");
			int orderStat = orderService.getOrderStatus(order_id);
			if(orderStat != HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER || 
					orderStat != HyperAppsConstants.ORDER_CANCELED_BY_RETAILER)
			{
				if(orderService.updateOrderStatus(order_id,orderAccepted))
				{
					LOGGER.info(this.getClass(),"ORDER UPDATED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					if(orderAccepted == 0)
					{
						message = HyperAppsConstants.ORDER_CANCEL_MESSAGE;
					}
					else
					{
						message = HyperAppsConstants.ORDER_ACCEPTED_MESSAGE;
					}
					sendNotificationsUpdateOrder(orderService.getCustomerIdByOrderId(order_id), orderAccepted);
					response.setMessage(message);
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
				}
				else
				{
					LOGGER.error(this.getClass(),"ORDER ACCEPTANCE FAILED");
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
					response.setMessage("Order Cancellation Failed");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
				}
				
			}
			else
			{
				LOGGER.error(this.getClass(),"ORDER ACCEPTANCE FAILED");
				response.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
				response.setMessage("Order already Cancelled");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
			}
			

	}


	public Object processOrder(String token, int order_id, String order_total, String order_grand_total,
			String order_details, String orderItems) {
		LOGGER.info(this.getClass(), "PROCESS ORDER BUSINESS LAYER");
		ResponseEntity<Object> respEntity = null;
		int orderStat = orderService.getOrderStatus(String.valueOf(order_id));
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
			if(orderStat != HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER || orderStat != HyperAppsConstants.ORDER_CANCELED_BY_RETAILER)
			{
				if (orderService.updateOrderStatus(String.valueOf(order_id),
						HyperAppsConstants.getNewOrderStatus(orderStat))) {
					orderService.updateOrderDetails(order_id, order_total, order_grand_total, order_details);
					LOGGER.info(this.getClass(), "ORDER UPDATED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("Order Updated Successfully");
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				} else {
					LOGGER.error(this.getClass(), "UNABLE TO UPDATED ORDER STATUS");
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
					response.setMessage("Unable to Updated Order Status");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				}
			}
			else
			{
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER STATUS");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated,Since Order Already " + HyperAppsConstants.getOrderStatus(orderStat));
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			}
			
		}
		return respEntity;
	}

	public Object cancelOrder(String token, int order_id, String order_details) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
				if (orderService.updateOrderStatus(String.valueOf(order_id),HyperAppsConstants.ORDER_CANCELED_BY_RETAILER)) {
					orderService.updateCancelledOrderDetails(order_id,order_details);
					LOGGER.info(this.getClass(), "ORDER CANCELLED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("Order Cancelled Successfully");
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				} else {
					LOGGER.error(this.getClass(), "UNABLE TO CANCEL ORDER STATUS");
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
					response.setMessage("Unable to Cancel Order Status");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				}
		}
		return respEntity;
	}

	public Object deliverOrder(String token, int order_id) {
		ResponseEntity<Object> respEntity = null;
		if ((respEntity = retailerValidationService.validateToken(token, respEntity)) == null) {
				if (orderService.updateOrderStatus(String.valueOf(order_id),HyperAppsConstants.ORDER_COMPLETED)) {
					LOGGER.info(this.getClass(), "ORDER DELIVERY UPDATED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("Order Deliver Updated Successfully");
					response.setError(HyperAppsConstants.RESPONSE_FALSE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				} else {
					LOGGER.error(this.getClass(), "UNABLE TO CANCEL ORDER STATUS");
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
					response.setMessage("Unable to Deliver Order");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					response.setData(null);
					apiResponse.setResponse(response);
					respEntity = new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
				}
		}
		return respEntity;
	}
	
	public void sendNotificationsUpdateOrder(int customer_id,int orderStat)
	{
		new Thread("BUSINESS PUSH NOTIFICAITON ORDER UPDATE THREAD")
		{
			public void run()
			{
				try {
					String message = null;
					switch(orderStat)
					{
					case 2:
					message = HyperAppsConstants.ORDER_UPDATE_PROCESSED;
					break;
					case 3:
					message = HyperAppsConstants.ORDER_UPDATE_CONFIRMED;
					break;
					case 4:
					message = HyperAppsConstants.ORDER_UPDATE_COMPLETED;
					break;
					case 5:
					message = HyperAppsConstants.ORDER_UPDATE_CANCELLED_BY_CUSTOMER;
					break;
					case 6:
					message = HyperAppsConstants.ORDER_UPDATE_CANCELLED_BY_RETAILER;
					break;
					}
					
					ArrayList<String> tokenArray = new ArrayList<String>();
						tokenArray.add(storeService.getDeviceToken(String.valueOf(customer_id)));
					LOGGER.info(getClass(), "CUSTOMER APP UPDATE NOTIFICATION TOKEN ARRAY SIZE "+tokenArray.size());
					LOGGER.info(getClass(), "CUSTOMER APP UPDATE NOTIFICATION TOKEN ARRAY "+tokenArray.toString());
					pushNotificationService.sendPushNotificationWithData(tokenArray
							,message, HyperAppsConstants.ORDER_UPDATE_TITLE);
					
					
					tokenArray = new ArrayList<String>();
					tokenArray = storeService.getBusinessDeviceToken(String.valueOf(customer_id));
					LOGGER.info(getClass(), "BUSINESS APP UPDATE NOTIFICATION TOKEN ARRAY SIZE "+tokenArray.size());
					LOGGER.info(getClass(), "BUSINESS APP UPDATE NOTIFICATION TOKEN ARRAY "+tokenArray.toString());
					pushNotificationService.sendPushNotificationWithData(tokenArray
							,message, HyperAppsConstants.ORDER_UPDATE_TITLE);
					
				} catch (Exception e) {
					LOGGER.error(this.getClass(),"EXCEPTION OCCURED IN UPDATE ORDER PUSH NOTIFICATION");
					e.printStackTrace();
				}
			}
		}.start();
		
		
	}

}
