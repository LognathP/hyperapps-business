package com.hyperapps.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CommonDataResponse {

	public String status;
	public String message;
	public List data;
}
