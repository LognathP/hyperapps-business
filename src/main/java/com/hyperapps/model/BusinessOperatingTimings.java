package com.hyperapps.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BusinessOperatingTimings {

	    public String day;
	    public String from;
	    public String to;
   
}
