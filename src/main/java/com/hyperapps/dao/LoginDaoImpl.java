package com.hyperapps.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.LoginQueryConstants;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.Login;
import com.hyperapps.util.CommonUtils;


@Component
public class LoginDaoImpl implements LoginDao {
	
	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	@Override
	public boolean checkUser(Login login) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean userAvailable = false;
		ResultSet res = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.LOGIN_USER_CHECK);
			preStmt.setString(1, login.getEmail());
			preStmt.setString(2, login.getPassword());
			res = preStmt.executeQuery();
			if (res.next()) {
				if (res.getInt(1) > 0) {
					userAvailable = true;
				}
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
		return userAvailable;
	}

	@Override
	public Login isNewUser(Login login) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		login.setUserAvailable(false);
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.GET_LOGIN_USER);
			preStmt.setString(1, login.getEmail());
			res = preStmt.executeQuery();
			if (res.next()) {
					login.setUserAvailable(true);
					login.setUserId(res.getString(1));
					login.setUserStatus(res.getString(2));
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isNewUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB isNewUser " + e.getMessage());
			}

		}
		return login;
	}

	@Override
	public void updateDeviceToken(Login login) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.UPDATE_USER_DEVICE_TOKEN);
			preStmt.setString(1, login.getDevice_token());
			preStmt.setString(2, login.getUserId());
			if (preStmt.executeUpdate()>0) {
				LOGGER.info(this.getClass(), "DEVICE TOKEN UPDATED SUCCESSFULLY");
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isNewUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB isNewUser " + e.getMessage());
			}

		}
		
	}
	
	@Override
	public void updateLoginToken(Login login) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.UPDATE_USER_LOGIN_TOKEN);
			preStmt.setString(1, login.getLoginToken());
			preStmt.setString(2, login.getUserId());
			if (preStmt.executeUpdate()>0) {
				LOGGER.info(this.getClass(), "LOGIN TOKEN UPDATED SUCCESSFULLY");
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE isNewUser " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, null, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB isNewUser " + e.getMessage());
			}

		}
		
	}
	
	@Override
	public boolean validateLoginToken(int userId,String token) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.CHECK_USER_TOKEN);
			preStmt.setString(1, token);
			preStmt.setInt(2, userId);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				stat = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE validateLoginToken " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB validateLoginToken " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean userIdValidation(int userId) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.CHECK_USER_ID);
			preStmt.setInt(1, userId);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				stat = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE userIdValidation " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON userIdValidation " + e.getMessage());
			}

		}
		return stat;
	}
	
	@Override
	public boolean validateToken(String token) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		boolean stat = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(LoginQueryConstants.CHECK_TOKEN);
			preStmt.setString(1, token);
			res = preStmt.executeQuery();
			if (res.next()) {
				if(res.getInt(1)>0)
				stat = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE validateToken " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				CommonUtils.closeDB(connection, res, preStmt);
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB validateToken " + e.getMessage());
			}

		}
		return stat;
	}
	
}
