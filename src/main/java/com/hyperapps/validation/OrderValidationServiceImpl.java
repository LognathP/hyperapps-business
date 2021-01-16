package com.hyperapps.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.constants.HyperAppsConstants;
import com.hyperapps.dao.OrderDao;
import com.hyperapps.dao.RetailerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.APIResponse;
import com.hyperapps.model.Response;
import com.hyperapps.model.User;
import com.hyperapps.service.LoginService;

@Component
public class OrderValidationServiceImpl implements OrderValidationService{

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	APIResponse apiResponse;
	
	@Autowired
	Response response;

	@Override
	public ResponseEntity<Object> validateOrderStatus(int orderUpdateStatus,int orderId,ResponseEntity<Object> respEntity) {
		LOGGER.info(this.getClass(),"VALIDATION ORDER STATUS VALDIATION LAYER");
		try {
			int status = orderDao.getOrderStatus(String.valueOf(orderId));
			switch (status) {
			case 1:
				break;
			case 2:
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated,Since Order Already " + HyperAppsConstants.getOrderStatus(status));
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			case 3:
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated,Since Order Already " + HyperAppsConstants.getOrderStatus(status));
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			case 4:
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated,Since Order Already " + HyperAppsConstants.getOrderStatus(status));
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			case 5:
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated,Since Order Already " + HyperAppsConstants.getOrderStatus(status));
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			case 6:
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated,Since Order Already " + HyperAppsConstants.getOrderStatus(status));
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
			default:
				LOGGER.error(this.getClass(),"UNABLE TO UPDATE ORDER");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
				response.setMessage("Order Cannot be Updated");
				response.setError(HyperAppsConstants.RESPONSE_TRUE);
				response.setData(null);
				apiResponse.setResponse(response);
				respEntity = new ResponseEntity<Object>(apiResponse,HttpStatus.OK);
				break;
			}
			
						
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE validateOrderStatus "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}
	


	
}
