package com.hyperapps.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface OrderValidationService {

	public ResponseEntity<Object> validateOrderStatus(int orderStatus,int orderId,ResponseEntity<Object> respEntity);
	
}
