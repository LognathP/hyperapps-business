package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DeliveryInfo {

	 @Qualifier("longitutde")
	 @JsonProperty("longitutde")
     public String longitutde;
	 @Qualifier("latitude")
	 @JsonProperty("latitude")
     public String latitude;
	 @Qualifier("address")
	 @JsonProperty("address")
     public String address;
	 @Qualifier("name")
	 @JsonProperty("name")
     public String name;
	 
}
