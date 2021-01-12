package com.hyperapps.validation;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.CustomerDao;
import com.hyperapps.dao.OrderDao;
import com.hyperapps.dao.RetailerDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.BusinessOperatingTimings;
import com.hyperapps.model.CommonResponse;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.Order;
import com.hyperapps.model.Store;
import com.hyperapps.request.OrderRequest;
import com.hyperapps.util.CalendarUtil;

@Component
public class RetailerValidationServiceImpl implements RetailerValidationService{

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	RetailerDao retailerDao;
	
	@Autowired
	CommonResponse commonResponse;
	
	@Override
	public ResponseEntity validateStoreId(int retailerId,ResponseEntity respEntity) {
		try {
			if(!retailerDao.isStoreAvailable(retailerId))
			{
				LOGGER.error(this.getClass(),"STORE NOT FOUND");
				commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
				commonResponse.setMessage("Store not Found");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE validateCustomerId "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}

	

	
}
