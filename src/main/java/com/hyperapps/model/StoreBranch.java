package com.hyperapps.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class StoreBranch {

    public int branch_id;
    public String branch_name;
    public String branch_address;
    public int branch_status;
    public String branch_phone;
    public String branch_latitude;
    public String branch_longitude;
    public int branch_delivery_radius;
    public int store_id;
   }
