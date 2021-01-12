package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionData {

   
	    public String promotion_url;
	    public String promotion_image_path;
	    public String discount_percentage;
	    public String promotion_title;
	    public int store_id;
	    public List<Product> products;
}
