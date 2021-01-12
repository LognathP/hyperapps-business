package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DeliveryAreas {

	  	@Qualifier("name")
	    @JsonProperty("name")
	    public String name;
	    @Qualifier("lat")
	    @JsonProperty("lat")
	    public String lat;
	    @Qualifier("lng")
	    @JsonProperty("lng")
	    public String lng;
   
}
