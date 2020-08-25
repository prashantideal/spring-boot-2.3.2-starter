package com.group.module.common;

public class ExceptionStatusCodeHolder {

    public static final Integer getCode(String errorCode) {
    	Integer statusCode = ((Integer.parseInt(errorCode)/1000)*100);
    	if(statusCode> 500)
    		statusCode = 500;
    	return statusCode;
    }
}