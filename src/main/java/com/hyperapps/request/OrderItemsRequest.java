package com.hyperapps.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemsRequest {

	 public int order_item_quantity;
	 public double price_per_unit;
	 public int product_id;
	 public double total;
}
