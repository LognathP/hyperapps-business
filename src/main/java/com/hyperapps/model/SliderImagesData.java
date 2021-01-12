package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SliderImagesData {

   
    public int store_id;
    public String imagepath;
    public List<Product> product_details;
}
