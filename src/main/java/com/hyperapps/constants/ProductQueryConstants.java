package com.hyperapps.constants;

public interface ProductQueryConstants {

	public String GET_ALL_CATEGORIES = "select r.id,r.name,r.image_path,r.active,r.category_status from rootcategories r,invrootcategories i where r.id = i.rootcategory_id\r\n" + 
			" and r.active = 1 and i.active = 1 and i.store_id = ?";
	
	public String GET_STORE_ROOT_CATEGORY="SELECT r.name,r.image_path,r.active,i.rootcategory_id,i.id FROM rootcategories r,invrootcategories i where r.id = i.rootcategory_id and i.store_id = ?";
	
	public String GET_STORE_PARENT_CATEGORY="SELECT p.id,p.rootcategory_id,ic.parentcategory_id,p.name,p.image_path,p.active from parentcategories p,invparentcategories ic where p.id = ic.parentcategory_id and p.rootcategory_id = ic.rootcategory_id and p.rootcategory_id = ? and ic.store_id = ?";
	
	public String GET_STORE_CHILD_CATEGORY="select c.id,c.rootcategory_id,c.parentcategory_id,c.active,c.name,c.image_path,c.IsDummy from childcategories c,invchildcategories i where c.rootcategory_id = i.rootcategory_id and c.parentcategory_id = i.parentcategory_id and c.rootcategory_id = ? and  c.parentcategory_id = ? and i.store_id = ?";
	
	public String GET_PRODUCT_DETAILS_BY_CATEGORY= "select p.id,p.name,p.category_id,p.description,p.image_path,p.active,i.product_id,i.store_id,i.price,\r\n" + 
			"i.special_price,i.promotional_price,i.weight,i.color,i.`size`,i.quantity,i.option1,i.option2 from products p,invproducts i where p.id = i.product_id and i.store_id = ? and i.category_id = ?";
	
	public String UPDATE_PARENT_CATEGORY_STATUS = "update invparentcategories set active = ? where parentcategory_id = ?";
	
	public String UPDATE_ROOT_CATEGORY_STATUS = "update invrootcategories set active = ? where id = ?";
	
	public String UPDATE_PRODUCT_CATEGORY_STATUS = "update invproducts set active = ? where product_id = ?";
	
	public String UPDATE_PRODUCT_DETAILS = "UPDATE invproducts SET price=?, special_price=?,weight=?,`size`=?, quantity=? WHERE product_id=?";
}
