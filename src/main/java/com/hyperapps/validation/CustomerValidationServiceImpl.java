package com.hyperapps.validation;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hyperapps.dao.CustomerDao;
import com.hyperapps.dao.OrderDao;
import com.hyperapps.logger.HyperAppsLogger;
import com.hyperapps.model.BusinessOperatingTimings;
import com.hyperapps.model.CommonResponse;
import com.hyperapps.model.DeliveryAreas;
import com.hyperapps.model.Order;
import com.hyperapps.model.Store;
import com.hyperapps.request.OrderRequest;
import com.hyperapps.util.CalendarUtil;

@Component
public class CustomerValidationServiceImpl implements CustomerValidationService{

	@Autowired
	HyperAppsLogger LOGGER;
	
	@Autowired
	CustomerDao customeDao;
	
	@Autowired
	CommonResponse commonResponse;
	
	@Override
	public ResponseEntity validateCustomerId(int customerId,ResponseEntity respEntity) {
		try {
			if(!customeDao.isCustomerAvailable(customerId))
			{
				LOGGER.error(this.getClass(),"CUSTOMER NOT FOUND");
				commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
				commonResponse.setMessage("Customer not Found");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE validateCustomerId "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	}

	@Override
	public ResponseEntity validateAddressId(int addressId, ResponseEntity respEntity) {
		try {
			if(!customeDao.isAddressAvailable(addressId))
			{
				LOGGER.error(this.getClass(),"ADDRESS NOT FOUND");
				commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
				commonResponse.setMessage("Address not Found");
				respEntity = new ResponseEntity<CommonResponse>(commonResponse,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.error(this.getClass(),"ERROR IN DB WHILE validateAddressId "+e.getMessage().toString());
			e.printStackTrace();
		}
		return respEntity;
	
	}

	
}
