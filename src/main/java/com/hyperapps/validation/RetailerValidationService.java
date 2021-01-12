package com.hyperapps.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hyperapps.request.OrderRequest;

@Service
public interface RetailerValidationService {

	public ResponseEntity validateStoreId(int retailerId,ResponseEntity respEntity);
	
}
