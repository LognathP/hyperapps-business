package com.hyperapps.business;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
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
import com.hyperapps.util.CalendarUtil;
import com.hyperapps.util.CommonUtils;

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
	
	@SuppressWarnings("unchecked")
	public Object getAllRetailerOrders(String storeId)
	{
		LOGGER.info(this.getClass(),"ALL RETAILER ORDER BUSINESS LAYER");
		List<Order> orderList = new ArrayList<Order>();
		JSONObject js = new JSONObject();
		orderList = orderService.getAllRetailerOrder(storeId);
		if(!orderList.isEmpty())
		{
			LOGGER.info(this.getClass(),"ORDER LISTED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Orders listed Successfully");
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			js.put("orders", orderList);
			response.setData(js);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		
		}
		else
		{
			LOGGER.error(this.getClass(),"ORDER LIST FAILED");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("No Orders Found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Object getAllCustomerOrders(String customerId)
	{
		LOGGER.info(this.getClass(),"ALL CUSTOMER ORDER BUSINESS LAYER");
		List<Order> orderList = new ArrayList<Order>();
		orderList = orderService.getAllCustomerOrder(customerId);
		JSONObject js = new JSONObject();
		if(!orderList.isEmpty())
		{
			LOGGER.info(this.getClass(),"ORDER LISTED SUCCESSFULLY");
			response.setStatus(HttpStatus.OK.toString());
			response.setMessage("Orders listed Successfully");
			response.setError(HyperAppsConstants.RESPONSE_FALSE);
			js.put("orders", orderList);
			response.setData(js);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
		
		}
		else
		{
			LOGGER.error(this.getClass(),"ORDER LIST FAILED");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("No Orders Found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			response.setData(null);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);		
		}
		
	}

	public Object cancelOrder(String order_id,int order_status) {
		LOGGER.info(this.getClass(),"RETAILER CANCEL ORDER BUSINESS LAYER");
		if(orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_INITIATED || 
				orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_PROCESSED)
		{
			if(orderService.updateOrderStatus(order_id,order_status))
			{
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

	

//	public Object updateOrderStatus(String order_id, int orderAccepted) {
//		if(orderAccepted == 0)
//		orderAccepted = HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER;
//		
//		if(orderAccepted == HyperAppsConstants.ORDER_ACCEPTED)
//		{
//			LOGGER.info(this.getClass(),"RETAILER ACCEPT ORDER BUSINESS LAYER");
//			if(orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER || 
//					orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_CANCELED_BY_RETAILER)
//			{
//				if(orderService.updateOrderStatus(order_id,orderAccepted))
//				{
//					LOGGER.info(this.getClass(),"ORDER ACCEPTED SUCCESSFULLY");
//					response.setStatus(HttpStatus.OK.toString());
//					response.setMessage("Order Cancelled Successfully");
//					response.setError(HyperAppsConstants.RESPONSE_FALSE);
//					apiResponse.setResponse(response);
//					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
//				}
//				else
//				{
//					LOGGER.error(this.getClass(),"ORDER ACCEPTANCE FAILED");
//					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//					response.setMessage("Order Cancellation Failed");
//					response.setError(HyperAppsConstants.RESPONSE_TRUE);
//					apiResponse.setResponse(response);
//					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
//				}
//				
//			}
//			else
//			{
//				LOGGER.error(this.getClass(),"ORDER ACCEPTANCE FAILED");
//				response.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
//				response.setMessage("Order already Cancelled");
//				response.setError(HyperAppsConstants.RESPONSE_TRUE);
//				apiResponse.setResponse(response);
//				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
//			}
//		}
//		else if(orderAccepted == HyperAppsConstants.ORDER_COMPLETED)
//		{
//			LOGGER.info(this.getClass(),"RETAILER COMPLETE ORDER BUSINESS LAYER");
//			if(orderService.getOrderStatus(order_id) == HyperAppsConstants.ORDER_ACCEPTED)
//			{
//				if(orderService.updateOrderStatus(order_id,orderAccepted))
//				{
//					LOGGER.info(this.getClass(),"ORDER COMPLETED SUCCESSFULLY");
//					response.setStatus(HttpStatus.OK.toString());
//					response.setMessage("Order Completed Successfully");
//					response.setError(HyperAppsConstants.RESPONSE_FALSE);
//					apiResponse.setResponse(response);
//					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);					}
//				else
//				{
//					LOGGER.error(this.getClass(),"ORDER COMPLETION FAILED");
//					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//					response.setMessage("Order Completion Failed");
//					response.setError(HyperAppsConstants.RESPONSE_TRUE);
//					apiResponse.setResponse(response);
//					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
//				}
//				
//			}
//			else
//			{
//				LOGGER.error(this.getClass(),"ORDER ACCEPTANCE FAILED");
//				response.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
//				response.setMessage("Order already Completed");
//				response.setError(HyperAppsConstants.RESPONSE_TRUE);
//				apiResponse.setResponse(response);
//				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
//			}
//		}
//		return new ResponseEntity<Object>(response,HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
	
	public Object updateOrderStatus(String order_id, int orderAccepted) {
		if(orderAccepted == 0)
		orderAccepted = HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER;
		
		String message;
			LOGGER.info(this.getClass(),"RETAILER ACCEPT ORDER BUSINESS LAYER");
			int orderStat = orderService.getOrderStatus(order_id);
			if(orderStat != HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER || 
					orderStat != HyperAppsConstants.ORDER_CANCELED_BY_CUSTOMER)
			{
				if(orderService.updateOrderStatus(order_id,orderAccepted))
				{
					LOGGER.info(this.getClass(),"ORDER ACCEPTED SUCCESSFULLY");
					response.setStatus(HttpStatus.OK.toString());
					if(orderAccepted == 0)
					{
						message = "Order Cancelled Successfully";
					}
					else
					{
						message = "Order Updated Successfully";
					}
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



	public Object placeOrder(int store_id, int customer_id, double order_total, double order_grand_total,
			String order_details, String order_items, String location, int offer_id, String payment_details) {
		Store store = new Store();
		LOGGER.info(this.getClass(),"REQUEST DETAILS IN BUSINESS=== STORE ID"+store_id+" CUSTOMER ID "+customer_id);
		OrderRequest orderReq = setOrderDetails(store_id, customer_id, order_total, order_grand_total,
				order_details, order_items, location, offer_id, payment_details);
		LOGGER.info(this.getClass(),"==========ORDER DETAILS=====");
		LOGGER.info(this.getClass(),orderReq.toString());
		store = orderService.getStoreDetails(store_id,store);
		LOGGER.info(this.getClass(),"==========STORE DETAILS=====");
		LOGGER.info(this.getClass(),String.valueOf(store.getStore_id()) + "--- "+store.toString());
		
		if(store.getStore_id()!=0)
		{
			store = validateDeliveryTime(store);
			if(store.isStoreTimeAvailable())
			{
				store = validateDeliveryLocation(store, orderReq);
				if(store.isDeliveryAvailable())
				{
					if(orderService.placeOrder(orderReq))
					{
						LOGGER.info(this.getClass(),"ORDER PLACED SUCCESSFULLY");
						response.setStatus(HttpStatus.OK.toString());
						response.setMessage("Order Placed Successfully");
						response.setError(HyperAppsConstants.RESPONSE_FALSE);
						apiResponse.setResponse(response);
						return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);						
					}
					else
					{
						LOGGER.error(this.getClass(),"UNABLE TO PLACE ORDER");
						response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
						response.setMessage("Unable to Place Order !!");
						response.setError(HyperAppsConstants.RESPONSE_TRUE);
						apiResponse.setResponse(response);
						return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
					}
				}
				else
				{
					LOGGER.error(this.getClass(),"STORE LOCATION AVAILABLITY NOT FOUND");
					response.setStatus(HttpStatus.NOT_FOUND.toString());
					response.setMessage("Expected delivery location not found !!");
					response.setError(HyperAppsConstants.RESPONSE_TRUE);
					apiResponse.setResponse(response);
					return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
				}

			}
			else
			{
				LOGGER.error(this.getClass(),"STORE TIME AVAILABLITY NOT FOUND");
				response.setStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Store Not Accepting New Orders at this Time!");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				apiResponse.setResponse(response);
				return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
			}
		}
		else
		{
		
			LOGGER.error(this.getClass(),"STORE NOT FOUND");
			response.setStatus(HttpStatus.NOT_FOUND.toString());
			response.setMessage("Store not Found");
			response.setError(HyperAppsConstants.RESPONSE_TRUE);
			apiResponse.setResponse(response);
			return new ResponseEntity<Object>(apiResponse,HttpStatus.OK);	
		}
		
	
	}

	public Store validateDeliveryTime(Store store) {
		List<Business_operating_timings> ls = store.getBusiness_operating_timings();
		if( ls!=null && ls.size()>0)
		{
			for (Business_operating_timings businessOperatingTimings : ls) {
				if(CalendarUtil.getCurrentDay().equalsIgnoreCase(businessOperatingTimings.getDay()))
				{
					if(CalendarUtil.getBusinessTimingStatus(businessOperatingTimings.getFrom(), businessOperatingTimings.getTo()))
					{
						store.setStoreTimeAvailable(true);
					}
				}
			}
		}
		return store;
		
	}
	
	public Store validateDeliveryLocation(Store store,OrderRequest orderReq) {
		List<DeliveryAreas> ls = store.getDelivery_areas();
		if(ls.size()>0)
		{
			for (DeliveryAreas areas : ls) {
				if(CommonUtils.distance(Double.parseDouble(orderReq.getLocation().getLat()),
						Double.parseDouble(orderReq.getLocation().getLng()),
						Double.parseDouble(areas.getLat()),
						Double.parseDouble(areas.getLng()),"K")<10)
				{
					store.setDeliveryAvailable(true);
				}
				
			}
					}
				
		return store;
	}
	
	public OrderRequest setOrderDetails(int store_id, int customer_id, double order_total, double order_grand_total,
			String order_details, String order_items, String location, int offer_id, String payment_details)
	{
		OrderRequest orderReq = new OrderRequest();
		try {
			orderReq.setStore_id(store_id);
			orderReq.setCustomer_id(customer_id);
			orderReq.setOrder_total(order_total);
			orderReq.setOrder_grand_total(order_grand_total);
			orderReq.setOrder_details(order_details);
			List<OrderItemsRequest> orList = new ArrayList<OrderItemsRequest>();
			JSONArray jsa = new JSONArray(order_items);
			for (int i = 0; i < jsa.length(); i++) {
				org.json.JSONObject js = jsa.getJSONObject(i);
				OrderItemsRequest ir = new OrderItemsRequest();
				ir.setOrder_item_quantity(js.getInt("order_item_quantity"));
				ir.setPrice_per_unit(Double.parseDouble(js.getString("price_per_unit")));
				ir.setProduct_id(js.getInt("product_id"));
				ir.setTotal(js.getDouble("total"));
				orList.add(ir);
			}
			orderReq.setOrder_items(orList);
			OrderLocationRequest ol = new OrderLocationRequest();
			JSONArray jsa1 = new JSONArray(location);
			for (int i = 0; i < jsa1.length(); i++) {
				org.json.JSONObject js = jsa1.getJSONObject(i);
				
				ol.setAddress(js.getString("address"));
				ol.setLat(js.getString("lat"));
				ol.setLng(js.getString("long"));
				ol.setName(js.getString("name"));
			}
			orderReq.setLocation(ol);
			org.json.JSONObject jsb = new org.json.JSONObject(payment_details);
			orderReq.setPayment_details(jsb.getString("payment_detail"));
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE setOrderDetails "+e.getMessage().toString());
			e.printStackTrace();
		} 
		
		return orderReq;
		
	}
		
}
