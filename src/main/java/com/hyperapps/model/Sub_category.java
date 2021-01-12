package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sub_category {

     public int id;
     public int rootcategory_id;
     public int parentcategory_id;
     public int store_id;
     public int active;
     public String name;
     public String image_path;
     public List<Child_category> child_category;
     public boolean showChildItem;
}
