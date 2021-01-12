package com.hyperapps.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Store{

	@Qualifier("id")
	@JsonProperty("id")
    public int store_id;
    @Qualifier("business_name")
    @JsonProperty("business_name")
    public String business_name;
    @Qualifier("business_short_desc")
    @JsonProperty("business_short_desc")
    public String business_short_desc;
    @Qualifier("user_image")
    @JsonProperty("user_image")
    public String user_image;
    @Qualifier("business_long_desc")
    @JsonProperty("business_long_desc")
    public String business_long_desc;
    @Qualifier("running_status")
    @JsonProperty("running_status")
    public int running_status;
    @Qualifier("physical_store_status")
    @JsonProperty("physical_store_status")
    public int physical_store_status;
    @Qualifier("physical_store_address")
    @JsonProperty("physical_store_address")
    public String physical_store_address;
    @Qualifier("business_phone")
    @JsonProperty("business_phone")
    public List<BusinessPhone> business_phone;
    @Qualifier("business_operating_mode")
    @JsonProperty("business_operating_mode")
    public int business_operating_mode;
    @Qualifier("business_operating_timings")
    @JsonProperty("business_operating_timings")
    public List<BusinessOperatingTimings> business_operating_timings;
    @Qualifier("delivery_areas")
    @JsonProperty("delivery_areas")
    public List<DeliveryAreas> delivery_areas;
    @Qualifier("home_delivery_status")
    @JsonProperty("home_delivery_status")
    public int home_delivery_status;
    @Qualifier("min_order_amount")
    @JsonProperty("min_order_amount")
    public String min_order_amount;
    @Qualifier("user_following_status")
    @JsonProperty("user_following_status")
    public int user_following_status;
    @Qualifier("delivery_charge")
    @JsonProperty("delivery_charge")
    public String delivery_charge;
    @Qualifier("store_tax_status")
    @JsonProperty("store_tax_status")
    public String store_tax_status;
    @Qualifier("store_tax_percentage")
    @JsonProperty("store_tax_percentage")
    public String store_tax_percentage;
    @Qualifier("store_tax_gst")
    @JsonProperty("store_tax_gst")
    public String store_tax_gst;
    @Qualifier("store_welcome_message")
    @JsonProperty("store_welcome_message")
    public String store_welcome_message;
    @Qualifier("free_delivery_above")
    @JsonProperty("free_delivery_above")
    public String free_delivery_above;
    @Qualifier("status")
    @JsonProperty("status")
    public String store_status;
    
    public boolean deliveryAvailable;
    public boolean storeTimeAvailable;
	
    @Override
	public String toString() {
		return "Store [store_id=" + store_id + ", business_name=" + business_name + ", business_short_desc="
				+ business_short_desc + ", user_image=" + user_image + ", business_long_desc=" + business_long_desc
				+ ", running_status=" + running_status + ", physical_store_status=" + physical_store_status
				+ ", physical_store_address=" + physical_store_address + ", business_phone=" + business_phone
				+ ", business_operating_mode=" + business_operating_mode + ", business_operating_timings="
				+ business_operating_timings + ", delivery_areas=" + delivery_areas + ", home_delivery_status="
				+ home_delivery_status + ", min_order_amount=" + min_order_amount + ", user_following_status="
				+ user_following_status + ", delivery_charge=" + delivery_charge + ", store_tax_status="
				+ store_tax_status + ", store_tax_percentage=" + store_tax_percentage + ", store_tax_gst="
				+ store_tax_gst + ", store_welcome_message=" + store_welcome_message + ", free_delivery_above="
				+ free_delivery_above + ", store_status=" + store_status + ", deliveryAvailable=" + deliveryAvailable
				+ ", storeTimeAvailable=" + storeTimeAvailable + "]";
	}
    
    
}



