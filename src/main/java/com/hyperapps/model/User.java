package com.hyperapps.model;


import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.hyperapps.util.AttributeEncryptor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	String name;
	String lastName;
	@NotBlank
	String email;
	@NotBlank
    @Convert(converter = AttributeEncryptor.class)
	String password;
	
	int status;
	@NotBlank
	String mobile;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;
	
	int parent_id;
	
	int user_group;
	int is_owner;
	int team_member_count;
	
	String orderprocesstype;
	
	int store_id;
	
	int forgot_password_pin;
}
