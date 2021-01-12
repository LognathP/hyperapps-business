package com.hyperapps.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HyperAppsLogger {

	public void error(Class<?> clazz,String msg)
	{
		Logger LOGGER = LoggerFactory.getLogger(clazz);
		LOGGER.error(msg);
		
	}
	
	
	public void info(Class<?> clazz,String msg)
	{
		Logger LOGGER = LoggerFactory.getLogger(clazz);
		LOGGER.info(msg);
	}
	
	
	public void debug(Class<?> clazz,String msg)
	{
		Logger LOGGER = LoggerFactory.getLogger(clazz);
		LOGGER.debug(msg);
	}
}
