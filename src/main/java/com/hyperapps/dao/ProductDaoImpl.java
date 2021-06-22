package com.hyperapps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.constants.ProductQueryConstants;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Category;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.Product;
import com.hyperapps.model.CategoryTree.Child_category;
import com.hyperapps.model.CategoryTree.Sub_category;
import com.hyperapps.util.CommonUtils;


@Component
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public List<Category> getStoreCategoryList(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<Category> catList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.GET_ALL_CATEGORIES);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Category cat = new Category();
				cat.setId(res.getInt(1));
				cat.setName(res.getString(2));
				cat.setImage_path(res.getString(3));
				cat.setActive(res.getInt(4));
				cat.setCategory_status(res.getInt(5));
				catList.add(cat);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getStoreCategoryList " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getStoreCategoryList " + e.getMessage());
			}

		}
		return catList;
	}
	
	@Override
	public List<CategoryTree> categoryTreeFetch(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt1 = null;
		ResultSet res1 = null;
		PreparedStatement preStmt2 = null;
		ResultSet res2 = null;
		List<CategoryTree> catList = new ArrayList<>();
   	

		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.GET_STORE_ROOT_CATEGORY);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				CategoryTree catTree = new CategoryTree();
				catTree.setName(res.getString(1));
				catTree.setImage_path(res.getString(2));
				catTree.setActive(res.getInt(3));
				catTree.setRootcategory_id(res.getInt(4));
				catTree.setId(res.getInt(5));
				preStmt1 = connection.prepareStatement(ProductQueryConstants.GET_STORE_PARENT_CATEGORY);
				preStmt1.setInt(1, catTree.getRootcategory_id());
				preStmt1.setInt(2, storeId);
				res1 = preStmt1.executeQuery();
				List<Sub_category> subCatList = new ArrayList<>();
				while(res1.next())
				{
					Sub_category subCat = new Sub_category();
					subCat.setId(res1.getInt(1));
					subCat.setRootcategory_id(res1.getInt(2));
					subCat.setParentcategory_id(res1.getInt(3));
					subCat.setName(res1.getString(4));
					subCat.setImage_path(res1.getString(5));
					subCat.setActive(res1.getInt(6));
					preStmt2 = connection.prepareStatement(ProductQueryConstants.GET_STORE_CHILD_CATEGORY);
					preStmt2.setInt(1, catTree.getRootcategory_id());
					preStmt2.setInt(2, subCat.getParentcategory_id());
					preStmt2.setInt(3, storeId);
					res2 = preStmt2.executeQuery();
					List<Child_category> childCatList = new ArrayList<>();
					while(res2.next())
					{
						Child_category childCat = new Child_category();
						childCat.setId(res2.getInt(1));
						childCat.setRootcategory_id(res2.getInt(2));
						childCat.setParentcategory_id(res2.getInt(3));
						childCat.setActive(res2.getInt(4));
						childCat.setName(res2.getString(5));
						childCat.setImage_path(res2.getString(6));
						childCat.setCategory_id(res2.getInt(1));
						childCatList.add(childCat);
					}
					res2.close();
					preStmt2.close();
					subCat.setChild_category(childCatList);
					subCatList.add(subCat);
				}
				res1.close();
				preStmt1.close();
				catTree.setSub_category(subCatList);
				catList.add(catTree);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE categoryTreeFetch " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON categoryTreeFetch " + e.getMessage());
			}

		}
		return catList;
	}
	
	@Override
	public List<Product> getProductsList(int storeId,int catgId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<Product> prodList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.GET_PRODUCT_DETAILS_BY_CATEGORY);
			preStmt.setInt(1, storeId);
			preStmt.setInt(2, catgId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Product products = new Product();
				products.setId(res.getInt("id"));
				products.setName(res.getString("name"));
				products.setCategory_id(res.getInt("category_id"));
				products.setDescription(res.getString("description"));
				products.setImage_path(res.getString("image_path"));
				products.setActive(res.getInt("active"));
				products.setProduct_id(res.getInt("product_id"));
				products.setStore_id(res.getInt("store_id"));
				products.setPrice(res.getString("price"));
				products.setSpecial_price(res.getString("special_price"));
				products.setPromotional_price(res.getString("promotional_price"));
				products.setWeight(res.getString("weight"));
				products.setColor(res.getString("color"));
				products.setSize(res.getString("size"));
				products.setQuantity(res.getInt("quantity"));
				products.setOption1(res.getString("option1"));
				products.setOption2(res.getString("option2"));
				prodList.add(products);
						
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getProductsList " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getProductsList " + e.getMessage());
			}

		}
		return prodList;
	}

	@Override
	public boolean updateParentCategory(int id, int active) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.UPDATE_PARENT_CATEGORY_STATUS);
			preStmt.setInt(1, active);
			preStmt.setInt(2, id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateParentCategory " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateParentCategory " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean updateRootCategory(int id, int active) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.UPDATE_ROOT_CATEGORY_STATUS);
			preStmt.setInt(1, active);
			preStmt.setInt(2, id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateRootCategory " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateRootCategory " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean updateProductStatus(int id, int active) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.UPDATE_PRODUCT_CATEGORY_STATUS);
			preStmt.setInt(1, active);
			preStmt.setInt(2, id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateProductStatus " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateProductStatus " + e.getMessage());
			}

		}
		return updStatus;
	}

	@Override
	public boolean updateProductData(int id, String price, String special_price, String weight, String size,
			int quantity) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(ProductQueryConstants.UPDATE_PRODUCT_DETAILS);
			preStmt.setDouble(1, Double.parseDouble(price));
			preStmt.setDouble(2, Double.parseDouble(special_price));
			preStmt.setDouble(3, Double.parseDouble(weight));
			preStmt.setString(4, size);
			preStmt.setInt(5, quantity);
			preStmt.setInt(6, id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateProductData " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateProductData " + e.getMessage());
			}

		}
		return updStatus;
	}
}
