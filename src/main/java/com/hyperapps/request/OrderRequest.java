package com.hyperapps.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequest {

	public int store_id;
	public int customer_id;
	public double order_total;
	public double order_grand_total;
	public String order_details;
	public List<OrderItemsRequest> order_items;
	public int offer_id;
	public OrderLocationRequest location;
	public String payment_details;
	
	
	
	
}
