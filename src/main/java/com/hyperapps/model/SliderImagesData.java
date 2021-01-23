package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SliderImagesData {

   
    public int store_id;
    public String imagepath;
    public List<Product> product_details;
}
