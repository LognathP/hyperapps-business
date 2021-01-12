package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryTree {

    public int id;
    public int rootcategory_id;
    public int store_id;
    public int active;
    public String name;
    public String image_path;
    public List<Sub_category> sub_category;
    public boolean showChildItem;
}
