package com.hyperapps.constants;

import java.util.HashMap;

public interface HyperAppsConstants {

	int RETAILER_USER = 1;

	int CUSTOMER_USER = 2;

	int DEVICE_ANDROID = 1;

	int DEVICE_IOS = 2;

	int DEVICE_WINDOWS = 3;

	int DEVICE_BB = 4;

	int ORDER_INITIATED = 1;
	int ORDER_PROCESSED = 2;
	int ORDER_ACCEPTED = 3;
	int ORDER_COMPLETED = 4;
	int ORDER_CANCELED_BY_CUSTOMER = 5;
	int ORDER_CANCELED_BY_RETAILER = 6;
	
	public static String getOrderStatus (int status)
	{
		final HashMap<Integer, String> orderStatus = new HashMap<Integer, String>();
		orderStatus.put(1, "Intiated");
		orderStatus.put(2, "Processed");
		orderStatus.put(3, "Accepted");
		orderStatus.put(4, "Completed");
		orderStatus.put(5, "Canceled by Customer");
		orderStatus.put(6, "Canceled by Retailed");
		
		return  orderStatus.get(status);
	}
	
	public static int getNewOrderStatus (int status)
	{
		final HashMap<Integer, Integer> orderStatus = new HashMap<Integer, Integer>();
		orderStatus.put(1, 2);
		orderStatus.put(2, 3);
		orderStatus.put(3, 4);
		return  orderStatus.get(status);
	}
	
	
	String RESPONSE_TRUE = "true";
	String RESPONSE_FALSE = "false";

	int ACTIVE = 1;
	
	String MAIL_NOTIFICATION = "Email";
	String PUSH_NOTIFICATION = "Push";
	
	String ORDER_UPDATE  = "Order Status Update";
	String ORDER_CANCEL_MESSAGE = "Order Cancelled Successfully";
	String ORDER_ACCEPTED_MESSAGE = "Order Accepted Successfully";

	String ORDER_UPDATE_TITLE = "Order Update";
	String ORDER_UPDATE_PROCESSED = "Your Order has been Processed Successfully !";
	String ORDER_UPDATE_CONFIRMED = "Your Order has been Confirmed by Retailer !";
	String ORDER_UPDATE_COMPLETED = "Your Order has been Completed Successfully !";
	String ORDER_UPDATE_CANCELLED_BY_CUSTOMER = "Your Order has been Cancelled !";
	String ORDER_UPDATE_CANCELLED_BY_RETAILER = "Your Order has been Cancelled by Retailer !";

	
}
