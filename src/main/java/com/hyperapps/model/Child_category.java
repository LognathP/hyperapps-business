package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Child_category {

    public int id;
    public int rootcategory_id;
    public int parentcategory_id;
    public int store_id;
    public int active;
    public int category_id;
    public String name;
    public String image_path;
    public int products;
    public boolean showChildItem;
    public int isDummy;
    public List<Product> product_list;
}
