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
import com.hyperapps.constants.LoginQueryConstants;
import com.hyperapps.constants.OrderQueryConstants;
import com.hyperapps.constants.RetailerQueryConstants;
import com.hyperapps.constants.StoreQueryConstants;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Categories;
import com.hyperapps.model.Category;
import com.hyperapps.model.Login;
import com.hyperapps.model.Profile;
import com.hyperapps.model.Profile.Business_operating_timings;
import com.hyperapps.model.Profile.Business_phone;
import com.hyperapps.request.OrderItemsRequest;
import com.hyperapps.request.OrderRequest;
import com.hyperapps.util.CommonUtils;


@Component
public class StoreDaoImpl implements StoreDao {
	
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
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_CATEGORIES);
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
	
	
	
}
