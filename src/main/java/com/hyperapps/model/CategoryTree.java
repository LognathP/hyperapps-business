package com.hyperapps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryTree {

	public int id;
	public int rootcategory_id;
	public int user_id;
	public int active;
	public String name;
	public String image_path;
	public List<Sub_category> sub_category;
	public boolean showChildItem;
	public int category_status;

	@Setter
	@Getter
	public static class Sub_category {
		public int id;
		public int rootcategory_id;
		public int parentcategory_id;
		public int user_id;
		public int active;
		public String name;
		public String image_path;
		public List<Child_category> child_category;
	}

	@Setter
	@Getter
	public static class Child_category {

		public int id;
		public int rootcategory_id;
		public int parentcategory_id;
		public int user_id;
		public int active;
		public String name;
		public String image_path;
		public int category_id;
	}
}
