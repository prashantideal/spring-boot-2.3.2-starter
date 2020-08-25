package com.group.module.common;



import lombok.Data;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Error implements Serializable {

	private static final long serialVersionUID = 56196438221373345L;

	String message;
	int code;
	int statusCode;
}
