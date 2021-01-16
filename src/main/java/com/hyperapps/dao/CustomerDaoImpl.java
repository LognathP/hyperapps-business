package com.hyperapps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hyperapps.constants.CustomerQueryConstants;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Categories;
import com.hyperapps.model.CategoryTree;
import com.hyperapps.model.CategoryTree.Child_category;
import com.hyperapps.model.Customer;
import com.hyperapps.model.CustomerAddress;
import com.hyperapps.model.UserProfile;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.OfferHistoryData;
import com.hyperapps.model.Product;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Profile.Business_phone;
import com.hyperapps.model.PromotionData;
import com.hyperapps.model.SliderImagesData;
import com.hyperapps.model.Store;
import com.hyperapps.request.AddAddressRequest;
import com.hyperapps.util.CommonUtils;


@Component
public class CustomerDaoImpl implements CustomerDao {
	
	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public Customer checkCustomer(Customer customer) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.LOGIN_CUSTOMER_CHECK);
			preStmt.setString(1, customer.getCustomers_telephone());
			res = preStmt.executeQuery();
			if (res.next()) {
				customer.setId(res.getLong(1));
				LOGGER.info(this.getClass(), "CUSTOMER ALREADY EXISTS - ID:" + customer.getId());
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE checkUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON checkUser " + e.getMessage());
			}

		}
		return customer;
	}

	@Override
	public int addCustomerAddress(AddAddressRequest req) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		ResultSet res = null;
		int id = 0;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSOMER_ADDRESS_INSERT,new String[] {"id"});
			preStmt.setInt(1, req.getCustomer_id());
			preStmt.setString(2, req.getAddress_label());
			preStmt.setString(3,req.getDoor_no());
			preStmt.setString(4,req.getStreet_name());
			preStmt.setString(5,req.getPin_code());
			preStmt.setString(6, req.getCity_name());
			preStmt.setString(7, req.getState());
			preStmt.setString(8, req.getCountry());
			preStmt.setString(9, req.getAddress_latitude());
			preStmt.setString(10, req.getAddress_longitude());
			int rs = preStmt.executeUpdate();
			if(rs > 0)
			{
				res = preStmt.getGeneratedKeys();
				if(res.next())
				{
					id = res.getInt(1);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addCustomerAddress " + e.getMessage());
			}

		}
		return id;
	}

	@Override
	public boolean updateCustomerAddress(AddAddressRequest req, int address_id) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		boolean stat = true;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSTOMER_ADDRESS_UPDATE);
			preStmt.setInt(1, req.getCustomer_id());
			preStmt.setString(2, req.getAddress_label());
			preStmt.setString(3,req.getDoor_no());
			preStmt.setString(4,req.getStreet_name());
			preStmt.setString(5,req.getPin_code());
			preStmt.setString(6, req.getCity_name());
			preStmt.setString(7, req.getState());
			preStmt.setString(8, req.getCountry());
			preStmt.setString(9, req.getAddress_latitude());
			preStmt.setString(10, req.getAddress_longitude());
			preStmt.setInt(11, address_id);
			preStmt.executeUpdate();
			
		} catch (Exception e) {
			stat = false;
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateCustomerAddress " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean deleteCustomerAddress(int address_id) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSTOMER_ADDRESS_DELETE);
			preStmt.setInt(1, address_id);
			int rs = preStmt.executeUpdate();
			if(rs > 0)
			{
				stat = true;
			}
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteCustomerAddress " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public List<CustomerAddress>  getCustomerAddress(int customerId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<CustomerAddress> custAddList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_CUSTOMER_ADDRESS);
			preStmt.setInt(1, customerId);
			res = preStmt.executeQuery();
			while(res.next()) {
				CustomerAddress custAddress = new CustomerAddress();
				custAddress.setId(res.getInt(1));
				custAddress.setCustomer_id(res.getInt(2));
				custAddress.setAddress_label(res.getString(3));
				custAddress.setDoor_no(res.getString(4));
				custAddress.setStreet_name(res.getString(5));
				custAddress.setCity_name(res.getString(6));
				custAddress.setPin_code(res.getString(7));
				custAddress.setState(res.getString(8));
				custAddress.setCountry(res.getString(9));
				custAddress.setAddress_latitude(res.getString(10));
				custAddress.setAddress_longitude(res.getString(11));
				custAddress.setCreated_at(res.getString(12));
				custAddress.setUpdated_at(res.getString(13));
				custAddress.setDeleted_at(res.getString(14));
				custAddList.add(custAddress);
				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCustomerAddress " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCustomerAddress " + e.getMessage());
			}

		}
		return custAddList;
	}
	
	@Override
	public boolean isCustomerAvailable(int customerId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSTOMER_EXIST_CHECK_QUERY);
			preStmt.setInt(1, customerId);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				{
					stat = true;
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isCustomerAvailable " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON isCustomerAvailable " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean isAddressAvailable(int addressId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.CUSTOMER_ADDRESS_EXIST_CHECK_QUERY);
			preStmt.setInt(1, addressId);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				{
					stat = true;
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isAddressAvailable " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON isAddressAvailable " + e.getMessage());
			}

		}
		return stat;
	}

	@Override
	public List<Categories> getCategories(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<Categories> catList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_ALL_CATEGORIES);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Categories cat = new Categories();
				cat.setId(res.getInt(1));
				cat.setName(res.getString(2));
				cat.setImage_path(res.getString(3));
				cat.setActive(res.getInt(4));
				catList.add(cat);				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCategories " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCategories " + e.getMessage());
			}

		}
		return catList;
	}
	
	
	
	@Override
	public UserProfile getCustomerProfile(int customer_id) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		UserProfile cpf = new UserProfile();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_CUSTOMER_PROFILE);
			preStmt.setInt(1, customer_id);
			res = preStmt.executeQuery();
			while(res.next()) {
				cpf.setCustomers_gender(res.getString("customers_gender"));
				cpf.setCustomers_firstname(res.getString("customers_firstname"));
				cpf.setCustomers_lastname(res.getString("customers_lastname"));
				cpf.setCustomers_dob(res.getString("customers_dob"));
				cpf.setCustomers_email_address(res.getString("customers_email_address"));
				cpf.setCustomers_default_address_id(res.getInt("customers_default_address_id"));
				cpf.setCustomers_telephone(res.getString("customers_telephone"));
				cpf.setCustomers_fax(res.getString("customers_fax"));
				cpf.setCustomers_password(res.getString("customers_password"));
				cpf.setCustomers_newsletter(res.getInt("customers_newsletter"));				
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCustomerProfile " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCustomerProfile " + e.getMessage());
			}

		}
		return cpf;
	}
	
	@Override
	public boolean updateCustomerProfile(UserProfile custProf) {
		PreparedStatement preStmt = null;
		Connection connection = null;
		boolean stat = true;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.UPDATED_CUSTOMER_PROFILE);
			preStmt.setString(1, custProf.getCustomers_firstname());
			preStmt.setString(2, custProf.getCustomers_lastname());
			preStmt.setString(3, custProf.getCustomers_dob());
			preStmt.setString(4, custProf.getCustomers_email_address());
			preStmt.setString(5, custProf.getCustomers_telephone());
			preStmt.setInt(6, custProf.getCustomers_default_address_id());
			preStmt.setString(7, custProf.getCustomers_fax());
			preStmt.setString(8, custProf.getCustomers_password());
			preStmt.setInt(9, custProf.getCustomers_newsletter());
			preStmt.setInt(10, Integer.valueOf(custProf.getCustomers_gender()));
			preStmt.setInt(11, custProf.getId());
			preStmt.executeUpdate();
			
		} catch (Exception e) {
			stat = false;
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateCustomerProfile " + e.getMessage());
			e.printStackTrace();
		} 
		finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateCustomerProfile " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public Store getStoreDeliverAreas(int storeId,String store_latitude, String store_longitude) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<DeliveryAreas> daList = new ArrayList<>();
		List<Business_phone> bpList = new ArrayList<>();
		List<Business_operating_timings> boList = new ArrayList<>();
		Store store = new Store();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_STORE_DETAILS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Gson gson = new Gson(); 
				DeliveryAreas[] userArray = gson.fromJson(res.getString("delivery_areas"), DeliveryAreas[].class); 
				for(DeliveryAreas df : userArray) {
					DeliveryAreas da = new DeliveryAreas();
					da.setName(df.getName());
					da.setLat(df.getLat());
					da.setLng(df.getLng());
					if(CommonUtils.distance(Double.parseDouble(store_latitude),
							Double.parseDouble(store_longitude),
							Double.parseDouble(df.getLat()),
							Double.parseDouble(df.getLng()),"K")<10)
					{
						daList.add(da);
						store.setDelivery_areas(daList);
						store.setStore_id(res.getInt("id"));
						store.setBusiness_name(res.getString("business_name"));
						store.setBusiness_short_desc(res.getString("business_short_desc"));
						store.setUser_image(res.getString("user_image"));
						store.setBusiness_long_desc(res.getString("business_long_desc"));
						store.setRunning_status(res.getInt("status"));
						store.setPhysical_store_status(res.getInt("physical_store_status"));
						store.setPhysical_store_address(res.getString("physical_store_address"));
						Business_phone[] phoneArray = new Gson().fromJson(res.getString("business_phone"), Business_phone[].class); 
						for(Business_phone bp : phoneArray) {
							Business_phone bph = new Business_phone();
							bph.setPhone(bp.getPhone());
							bpList.add(bph);
						}
						store.setBusiness_operating_mode(res.getInt("business_operating_mode"));
						Business_operating_timings[] operationArray = new Gson().fromJson(res.getString("business_operating_timings"), Business_operating_timings[].class); 
						for(Business_operating_timings bop : operationArray) {
							Business_operating_timings bopt = new Business_operating_timings();
							bopt.setDay(bop.getDay());
							bopt.setFrom(bop.getFrom());
							bopt.setTo(bop.getTo());
							boList.add(bopt);
						}
						store.setBusiness_operating_timings(boList);
						store.setHome_delivery_status(res.getInt("home_delivery"));
						store.setMin_order_amount(res.getString("min_order_amount"));
						store.setDelivery_charge(res.getString("delivery_charge"));
						store.setStore_tax_status(res.getString("store_tax_status"));
						store.setStore_tax_gst(res.getString("store_tax_gst"));
						store.setStore_tax_percentage(res.getString("store_tax_percentage"));
						store.setStore_status(res.getString("status"));
									
					}
				}	
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getStoreDeliverAreas " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getStoreDeliverAreas " + e.getMessage());
			}

		}
		return store;
	}
	
	@Override
	public List<SliderImagesData> getSliderImages(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt1 = null;
		ResultSet res1 = null;
		
		List<SliderImagesData> slideList = new ArrayList<>();
    	

		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_SLIDER_DETAILS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				SliderImagesData sliderImgs = new SliderImagesData();
				sliderImgs.setStore_id(res.getInt(1));
				sliderImgs.setImagepath(res.getString(2));
				List<String> productList = Arrays.asList(res.getString(3).split(","));
				List<Product> prodList = new ArrayList<>();
				for (String prodId : productList) {
					preStmt1 = connection.prepareStatement(CustomerQueryConstants.GET_PRODUCT_DETAILS_BY_ID);
					preStmt1.setString(1, prodId);
					res1 = preStmt1.executeQuery();
					while(res1.next())
					{
						Product products = new Product();
						products.setId(res1.getInt("id"));
						products.setName(res1.getString("name"));
						products.setCategory_id(res1.getInt("category_id"));
						products.setDescription(res1.getString("description"));
						products.setImage_path(res1.getString("image_path"));
						products.setActive(res1.getInt("active"));
						products.setProduct_id(res1.getInt("product_id"));
						products.setStore_id(res1.getInt("store_id"));
						products.setPrice(res1.getString("price"));
						products.setSpecial_price(res1.getString("special_price"));
						products.setPromotional_price(res1.getString("promotional_price"));
						products.setWeight(res1.getString("weight"));
						products.setColor(res1.getString("color"));
						products.setSize(res1.getString("size"));
						products.setQuantity(res1.getInt("quantity"));
						products.setOption1(res1.getString("option1"));
						products.setOption2(res1.getString("option2"));
						prodList.add(products);
					}
					res1.close();
					preStmt1.close();
				}
				sliderImgs.setProduct_details(prodList);
				slideList.add(sliderImgs);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getSliderImages " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getSliderImages " + e.getMessage());
			}

		}
		return slideList;
	}
	
	@Override
	public List<PromotionData> getPromotions(int storeId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt1 = null;
		ResultSet res1 = null;
		
		List<PromotionData> promoList = new ArrayList<>();
    	

		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_PROMOTIONS_DETAILS);
			preStmt.setInt(1, storeId);
			res = preStmt.executeQuery();
			while(res.next()) {
				PromotionData promo = new PromotionData();
				promo.setPromotion_url(res.getString(1));
				promo.setPromotion_title(res.getString(2));
				promo.setDiscount_percentage(res.getString(3));
				promo.setPromotion_image_path(res.getString(4));
				promo.setStore_id(res.getInt(5));
				List<String> productList = Arrays.asList(res.getString(6).split(","));
				List<Product> prodList = new ArrayList<>();
				for (String prodId : productList) {
					preStmt1 = connection.prepareStatement(CustomerQueryConstants.GET_PRODUCT_DETAILS_BY_ID);
					preStmt1.setString(1, prodId);
					res1 = preStmt1.executeQuery();
					while(res1.next())
					{
						Product products = new Product();
						products.setId(res1.getInt("id"));
						products.setName(res1.getString("name"));
						products.setCategory_id(res1.getInt("category_id"));
						products.setDescription(res1.getString("description"));
						products.setImage_path(res1.getString("image_path"));
						products.setActive(res1.getInt("active"));
						products.setProduct_id(res1.getInt("product_id"));
						products.setStore_id(res1.getInt("store_id"));
						products.setPrice(res1.getString("price"));
						products.setSpecial_price(res1.getString("special_price"));
						products.setPromotional_price(res1.getString("promotional_price"));
						products.setWeight(res1.getString("weight"));
						products.setColor(res1.getString("color"));
						products.setSize(res1.getString("size"));
						products.setQuantity(res1.getInt("quantity"));
						products.setOption1(res1.getString("option1"));
						products.setOption2(res1.getString("option2"));
						prodList.add(products);
					}
					res1.close();
					preStmt1.close();
				}
				promo.setProducts(prodList);
				promoList.add(promo);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getPromotions " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getPromotions " + e.getMessage());
			}

		}
		return promoList;
	}
	
	@Override
	public List<Product> searchProduct(String storied, String searchstr) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		List<Product> prodList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_PRODUCT_DETAILS_BY_STOREID);
			preStmt.setInt(1, Integer.parseInt(storied));
			preStmt.setString(2, "%" + searchstr + "%");
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
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getPromotions " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getPromotions " + e.getMessage());
			}

		}
		return prodList;
	}
	
	@Override
	public List<Child_category> getCategoryDetails(int store_id, int parentCatgoryId, int subCategoryId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt1 = null;
		ResultSet res1 = null;
		List<Product> prodList = new ArrayList<>();
    	List<Child_category> childCatList = new ArrayList<>();

		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(CustomerQueryConstants.GET_STORE_CHILD_CATEGORY_BY_STORE);
			preStmt.setInt(1, parentCatgoryId);
			preStmt.setInt(2, subCategoryId);
			preStmt.setInt(3, store_id);
			res = preStmt.executeQuery();
			while(res.next())
			{
				Child_category childCat = new Child_category();
				childCat.setId(res.getInt(1));
				childCat.setRootcategory_id(res.getInt(2));
				childCat.setParentcategory_id(res.getInt(3));
				childCat.setActive(res.getInt(4));
				childCat.setName(res.getString(5));
				childCat.setImage_path(res.getString(6));
				preStmt1 = connection.prepareStatement(CustomerQueryConstants.GET_PRODUCT_DETAILS_BY_CATEGORY);
				preStmt1.setInt(1, store_id);
				preStmt1.setInt(2, childCat.getId());
				res1 = preStmt1.executeQuery();
				while(res1.next())
				{
					Product products = new Product();
					products.setId(res1.getInt("id"));
					products.setName(res1.getString("name"));
					products.setCategory_id(res1.getInt("category_id"));
					products.setDescription(res1.getString("description"));
					products.setImage_path(res1.getString("image_path"));
					products.setActive(res1.getInt("active"));
					products.setProduct_id(res1.getInt("product_id"));
					products.setStore_id(res1.getInt("store_id"));
					products.setPrice(res1.getString("price"));
					products.setSpecial_price(res1.getString("special_price"));
					products.setPromotional_price(res1.getString("promotional_price"));
					products.setWeight(res1.getString("weight"));
					products.setColor(res1.getString("color"));
					products.setSize(res1.getString("size"));
					products.setQuantity(res1.getInt("quantity"));
					products.setOption1(res1.getString("option1"));
					products.setOption2(res1.getString("option2"));
					prodList.add(products);
				}
				res1.close();
				preStmt1.close();
				childCatList.add(childCat);
			}
				
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getCategoryDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
				
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getCategoryDetails " + e.getMessage());
			}

		}
		return childCatList;
	}
	
	
	

}
