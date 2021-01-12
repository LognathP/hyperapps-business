package com.hyperapps.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hyperapps.request.OrderRequest;

@Service
public interface CustomerValidationService {

	public ResponseEntity validateCustomerId(int customerId,ResponseEntity respEntity);

	public ResponseEntity validateAddressId(int addressId, ResponseEntity respEntity);
	
	
}
