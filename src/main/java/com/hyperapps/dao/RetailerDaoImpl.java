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
import com.hyperapps.constants.OrderQueryConstants;
import com.hyperapps.constants.RetailerQueryConstants;
import com.hyperapps.logger.ConfigProperties;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Category;
import com.hyperapps.model.Customer;
import com.hyperapps.model.Product;
import com.hyperapps.model.Profile;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Profile.Business_phone;
import com.hyperapps.model.User;
import com.hyperapps.model.User.Team_members;
import com.hyperapps.model.User.Welcome_content;
import com.hyperapps.request.ProfileUpdateRequest;
import com.hyperapps.util.CommonUtils;
import com.hyperapps.util.DESEncryptor;


@Component
public class RetailerDaoImpl implements RetailerDao {
	
	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Autowired
	ConfigProperties configProp;
	
	
	@Override
	public boolean isStoreAvailable(int retailerId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.STORE_ID_CHECK);
			preStmt.setInt(1, retailerId);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				{
					stat = true;
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isStoreAvailable " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON isStoreAvailable " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public User getUserDetails(String token) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt1 = null;
		ResultSet res1 = null;
		int owner = 0;
		User user = new User();
		Welcome_content welcome = new Welcome_content();
		List<Team_members> tlist = new ArrayList<User.Team_members>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.GET_USER_DETAILS);
			preStmt.setString(1, token);
			res = preStmt.executeQuery();
			if(res.next()) {
				user.setUser_id(res.getInt(1));
				user.setName(res.getString(2));
				user.setEmail(res.getString(3));
				user.setParent_id(res.getInt(4));
				user.setUser_group(res.getInt(5));
				user.setStore_id(res.getInt(6));
				owner = res.getInt(7);
				welcome.setMessage(res.getString(8));
				welcome.setDesignation(res.getInt(9));
				user.setWelcome_content(welcome);
				preStmt1 = connection.prepareStatement(RetailerQueryConstants.GET_STORE_PROFILE_DETAILS);
				preStmt1.setInt(1, user.getUser_id());
				res1 = preStmt1.executeQuery();
				if(res1.next())
				{
					user.setBusiness_name(res1.getString(1));
					user.setBusiness_short_desc(res1.getString(2));
					user.setBusiness_long_desc(res1.getString(3));
					user.setStore_status(res1.getInt(4));
					user.setStore_id(res1.getInt(5));
				}
				preStmt1.close();
				res1.close();
				if(owner>0)
				{
					preStmt1 = connection.prepareStatement(RetailerQueryConstants.GET_TEAM_MEMBERS_DETAILS);
					preStmt1.setInt(1, user.getUser_id());
					res1 = preStmt1.executeQuery();
					while(res1.next())
					{
					Team_members team = new Team_members();	
					team.setId(res1.getInt(1));
					team.setName(res1.getString(2));
					team.setEmail(res1.getString(3));
					team.setStatus(res1.getInt(4));
					team.setMobile(res1.getString(5));
					team.setParent_id(res1.getInt(6));
					team.setUser_group(res1.getInt(7));
					team.setCreated_at(res1.getString(8));
					team.setUpdated_at(res1.getString(9));
					tlist.add(team);
					}
					user.setTeam_members(tlist);
					res1.close();
					preStmt1.close();
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getUserDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getUserDetails " + e.getMessage());
			}

		}
		return user;
	}
	
	
	@Override
	public List<Profile> getProfileDetails(int userId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		PreparedStatement preStmt1 = null;
		ResultSet res1 = null;
		List<Profile> profileList = new ArrayList<Profile>();
		List<Business_phone> bhList = new ArrayList<Business_phone>();
		List<Business_operating_timings> boList = new ArrayList<Business_operating_timings>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.GET_PROFILE_DETAILS);
			preStmt.setInt(1, userId);
			res = preStmt.executeQuery();
			while(res.next()) {
				Profile prof = new Profile();
				prof.setId(res.getInt(1));
				prof.setBusiness_name(res.getString(2));
				prof.setBusiness_short_desc(res.getString(3));
				prof.setBusiness_long_desc(res.getString(4));
				List<String> catList = Arrays.asList(res.getString(5).split(","));
				List<Category> catDetailsList = new ArrayList<>();
				for (String catId : catList) {
					preStmt1 = connection.prepareStatement(RetailerQueryConstants.GET_CATEGORY_DETAILS);
					preStmt1.setString(1, catId);
					res1 = preStmt1.executeQuery();
					while(res1.next())
					{
						Category cat = new Category();
						cat.setId(res1.getInt(1));
						cat.setName(res1.getString(2));
						cat.setImage_path(res1.getString(3));
						cat.setActive(res1.getInt(4));
						cat.setCategory_status(res1.getInt(5));
						catDetailsList.add(cat);
					}
					res1.close();
					preStmt1.close();
				}
				prof.setCategory_list(catDetailsList);
				prof.setPhysical_store_status(res.getInt(6));
				prof.setPhysical_store_address(res.getString(7));
				Business_phone[] phoneArray = new Gson().fromJson(res.getString(8),
						Business_phone[].class);
				for (Business_phone bp : phoneArray) {
					Business_phone bph = new Business_phone();
					bph.setPhone(bp.getPhone());
					bhList.add(bph);
				}
				prof.setBusiness_phone(bhList);
				prof.setBusiness_operating_mode(res.getInt(9));
				Business_operating_timings[] operationArray = new Gson()
						.fromJson(res.getString(10), Business_operating_timings[].class);
				for (Business_operating_timings bop : operationArray) {
					Business_operating_timings bopt = new Business_operating_timings();
					bopt.setDay(bop.getDay());
					bopt.setFrom(bop.getFrom());
					bopt.setTo(bop.getTo());
					boList.add(bopt);
				}
				prof.setBusiness_operating_timings(boList);
				prof.setStatus(res.getInt(11));
				prof.setCreated_at(res.getString(12));
				prof.setUpdated_at(res.getString(13));
				prof.setUser_id(res.getInt(14));
				prof.setUser_image(res.getString(15));
				prof.setStore_tax_status(res.getInt(16));
				prof.setStore_tax_percentage(res.getString(17));
				prof.setStore_tax_gst(res.getString(18));
				profileList.add(prof);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getProfileDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getProfileDetails " + e.getMessage());
			}

		}
		return profileList;
	}
	
	
	@Override
	public boolean updateUserProfile(ProfileUpdateRequest prof,int userImageFlag) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.UPDATE_PROFILE_DETAILS);
			preStmt.setString(1, prof.getBusiness_name());
			preStmt.setString(2, prof.getBusiness_short_desc());
			preStmt.setString(3, prof.getBusiness_long_desc());
			preStmt.setString(4, prof.getCategory_list());
			preStmt.setInt(5, prof.getPhysical_store_status());
			preStmt.setString(6, prof.getPhysical_store_address());
			preStmt.setString(7, prof.getBusiness_phone());
			preStmt.setInt(8, prof.getBusiness_operating_mode());
			preStmt.setString(9, prof.getBusiness_operating_timings());
			preStmt.setInt(10, prof.getStatus());
			preStmt.setInt(11, prof.getUser_id());
			preStmt.setString(12, null);
			if(userImageFlag == 1)
			{
				preStmt.setString(12, prof.getUserImage());
			}
			preStmt.setInt(13, prof.getId());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateUserProfile " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateUserProfile " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	
	
	@Override
	public boolean isMobileNumberExists(String mobileNum) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.MOBILE_NUM_EXIST_CHECK);
			preStmt.setString(1, mobileNum);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				{
					stat = true;
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isMobileNumberExists " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON isMobileNumberExists " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean isEmailIdExists(String emailId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.EMAIL_EXIST_CHECK);
			preStmt.setString(1, emailId);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				{
					stat = true;
				}
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isEmailIdExists " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON isEmailIdExists " + e.getMessage());
			}

		}
		return stat;
	}
	
	
	@Override
	public User getParentUserDetails(int userId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		User user = new User();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.GET_PARENTUSER_DETAILS);
			preStmt.setInt(1, userId);
			res = preStmt.executeQuery();
			if(res.next()) {
				user.setUser_id(res.getInt(1));
				user.setName(res.getString(2));
				user.setIsOwner(res.getInt(3));
				user.setTeamMemberCount(res.getInt(4));
				}
			
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getParentUserDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON getParentUserDetails " + e.getMessage());
			}

		}
		return user;
	}
	
	@Override
	public boolean addTeamMember(int store_id, int user_id, String name, String mobile, int access_type, String email) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean flag = false;
		int res = 0;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.ADD_TEAM_MEMBER);
			preStmt.setString(1, name);
			preStmt.setString(2, email);
			preStmt.setString(3, mobile);
			preStmt.setInt(4, user_id);
			preStmt.setInt(5, access_type);
			preStmt.setInt(6, store_id);
			preStmt.setString(7, DESEncryptor.encrypt(name.substring(0, 4)+mobile.substring(0,4), configProp.getConfigValue("enc.key")));
			res = preStmt.executeUpdate();
			if(res>0)
			{
				updateUserTeamCount(user_id);
				flag = true;
			}
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addTeamMember " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addTeamMember " + e.getMessage());
			}

		}
		return flag;
	}
	
	public void updateUserTeamCount (int userId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.UPDATE_TEAM_COUNT);
			preStmt.setInt(1, userId);
			preStmt.executeUpdate();

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateUserTeamCount " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateUserTeamCount " + e.getMessage());
			}

		}
	}
	
	public boolean removeTeamMember(int user_id, String email) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.REMOVE_TEAM_MEMBER);
			preStmt.setString(1, email);
			preStmt.setInt(2, user_id);
			int res = preStmt.executeUpdate();
			if(res>0)
			{
				stat = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE removeTeamMember " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB removeTeamMember " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean addCustomer(String customers_firstname, String customers_telephone, String customers_email_address){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean flag = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.ADD_CUSTOMER);
			preStmt.setString(1, customers_firstname);
			preStmt.setString(2, customers_email_address);
			preStmt.setString(3, customers_telephone);
			if(preStmt.executeUpdate()>0)
			flag = true;
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addCustomer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addCustomer " + e.getMessage());
			}

		}
		return flag;
	}
	
	@Override
	public boolean addfeedback(int user_id, String details){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean flag = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.ADD_FEEDBACK);
			preStmt.setString(1, details);
			preStmt.setInt(2, user_id);
			if(preStmt.executeUpdate()>0)
			flag = true;
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addfeedback " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addfeedback " + e.getMessage());
			}

		}
		return flag;
	}

	@Override
	public List<Customer> fetchCustomerList(int customer_type) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		List<Customer> custList = new ArrayList<Customer>();
		ResultSet res = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(RetailerQueryConstants.GET_CUSTOMER_LIST);
			preStmt.setInt(1, customer_type);
			res = preStmt.executeQuery();
			while(res.next())
			{
				Customer cust = new Customer();
				cust.setId(res.getInt(1));
				cust.setCustomers_gender(res.getString(2));
				cust.setCustomers_firstname(res.getString(3));
				cust.setCustomers_lastname(res.getString(4));
				cust.setCustomers_dob(res.getString(5));
				cust.setCustomers_email_address(res.getString(6));
				cust.setCustomers_default_address_id(res.getString(7));
				cust.setCustomers_telephone(res.getString(8));
				cust.setCustomers_fax(res.getString(9));
				cust.setCustomers_password(res.getString(10));
				cust.setCustomers_newsletter(res.getString(11));
				cust.setCreated_at(res.getString(12));
				cust.setUpdated_at(res.getString(13));
				cust.setOtp(res.getString(14));
				cust.setType(res.getString(15));
				cust.setEnable(res.getString(16));
				cust.setCustomer_type(res.getString(17));
				cust.setCustom_message(res.getString(18));
				cust.setStore_id(res.getString(19));
				cust.setSelected(res.getInt(20) == 0 ? false : true);
				custList.add(cust);
			}
						

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE fetchCustomerList " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB fetchCustomerList " + e.getMessage());
			}

		}
		return custList;
	}
}
