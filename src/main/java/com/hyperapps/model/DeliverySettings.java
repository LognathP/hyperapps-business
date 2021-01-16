package com.hyperapps.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliverySettings {

    public int id;
    public int delivery_type;
    public String min_order_amount;
    public String delivery_charge;
    public String free_delivery_above;
    public List<Delivery_areas> delivery_areas;
    public int home_delivery;
    public int store_id;

    @Getter
    @Setter
    public static class Delivery_areas {
        public String name;
        public String lat;
        @SerializedName("long")
        public String mlong;
    }
}
