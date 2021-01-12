package com.hyperapps.model;


import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Order extends CustomerInfo {

	@Qualifier("order_id")
	@JsonProperty("order_id")
    public int order_id;
    @Qualifier("order_message")
    @JsonProperty("order_message")
    public String order_message;
    @Qualifier("order_placed_date")
    @JsonProperty("order_placed_date")
    public String order_placed_date;
    @Qualifier("order_updated_at")
    @JsonProperty("order_updated_at")
    public String order_updated_at;
    @Qualifier("customer_id")
    @JsonProperty("customer_id")
    public int customer_id;
    @Qualifier("order_status")
    @JsonProperty("order_status")
    public int order_status;
    @Qualifier("retailer_id")
    @JsonProperty("retailer_id")
    public int retailer_id;
    
    @Qualifier("order_total")
    @JsonProperty("order_total")
    public String order_total;
    
    @Qualifier("order_grand_total")
    @JsonProperty("order_grand_total")
    public String order_grand_total;
    @Qualifier("customer_info")
    @JsonProperty("customer_info")
    public CustomerInfo customer_info;
    
    @Qualifier("order_items")
    @JsonProperty("order_items")
    public List<OrderItems> order_items;
    @Qualifier("offer_details")
    @JsonProperty("offer_details")
    public OfferHistoryData offer_details;
    @Qualifier("payment_details")
    @JsonProperty("payment_details")
    public PaymentResponse payment_details;
    @Qualifier("delivery_info")
    @JsonProperty("delivery_info")
    public DeliveryInfo deliveryInfo;
    
}

