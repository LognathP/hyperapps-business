package com.hyperapps.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hyperapps.request.OrderRequest;

@Service
public interface RetailerValidationService {

	public ResponseEntity validateStoreId(int retailerId,ResponseEntity respEntity);
	
	public ResponseEntity<Object> validateToken(String token,ResponseEntity<Object> respEntity);
	
	public ResponseEntity<Object> parentUserValidation(int userId,ResponseEntity<Object> respEntity);
	
	public ResponseEntity<Object> isMobileNumberExists(String mobile,ResponseEntity<Object> respEntity);
	
	public ResponseEntity<Object> isEmailIdExists(String email,ResponseEntity<Object> respEntity);
	
	public ResponseEntity<Object> userIdExistValidation(int userId,ResponseEntity<Object> respEntity);
	
}
