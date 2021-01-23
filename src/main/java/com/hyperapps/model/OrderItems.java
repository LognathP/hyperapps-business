package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class OrderItems {

	@Qualifier("order_item_id")
	@JsonProperty("order_item_id")
    public int order_item_id;
    @Qualifier("name")
    @JsonProperty("name")
    public String name;
    @Qualifier("image_path")
    @JsonProperty("image_path")
    public String image_path;
    @Qualifier("order_item_quantity")
    @JsonProperty("order_item_quantity")
    public int order_item_quantity;
    @Qualifier("price_per_unit")
    @JsonProperty("price_per_unit")
    public String price_per_unit;
    @Qualifier("item_status")
    @JsonProperty("item_status")
    public int item_status;
    @Qualifier("total")
    @JsonProperty("total")
    public String total;
    public boolean isSelected;
    public boolean isActive;
    public Product product_info;
}
