package com.hyperapps.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	@Override
	public String toString() {
		return "OrderRequest [store_id=" + store_id + ", customer_id=" + customer_id + ", order_total=" + order_total
				+ ", order_grand_total=" + order_grand_total + ", order_details=" + order_details + ", order_items="
				+ order_items + ", offer_id=" + offer_id + ", location=" + location + ", payment_details="
				+ payment_details + "]";
	}
	
	
}
