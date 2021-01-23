package com.hyperapps.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Component
@ToString
public class APIResponse {

	Response response;
}
