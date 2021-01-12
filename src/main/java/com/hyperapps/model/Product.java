package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Product {

	@Qualifier("id")
	@JsonProperty("id")
    public int id;
    @Qualifier("name")
    @JsonProperty("name")
    public String name;
    @Qualifier("category_id")
    @JsonProperty("category_id")
    public int category_id;
    @Qualifier("description")
    @JsonProperty("description")
    public String description;
    @Qualifier("image_path")
    @JsonProperty("image_path")
    public String image_path;
    @Qualifier("active")
    @JsonProperty("active")
    public int active;
    @Qualifier("store_id")
    @JsonProperty("store_id")
    public int store_id;
    @Qualifier("product_id")
    @JsonProperty("product_id")
    public int product_id;
    @Qualifier("user_id")
    @JsonProperty("user_id")
    public int user_id;
    @Qualifier("price")
    @JsonProperty("price")
    public String price;
    @Qualifier("special_price")
    @JsonProperty("special_price")
    public String special_price;
    @Qualifier("promotional_price")
    @JsonProperty("promotional_price")
    public String promotional_price;
    @Qualifier("weight")
    @JsonProperty("weight")
    public String weight;
    @Qualifier("color")
    @JsonProperty("color")
    public String color;
    @Qualifier("size")
    @JsonProperty("size")
    public String size;
    @Qualifier("quantity")
    @JsonProperty("quantity")
    public int quantity;
    @Qualifier("option1")
    @JsonProperty("option1")
    public String option1;
    @Qualifier("option2")
    @JsonProperty("option2")
    public String option2;
    public boolean isEditEnable;
}
