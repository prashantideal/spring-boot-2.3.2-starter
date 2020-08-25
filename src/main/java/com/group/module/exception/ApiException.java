/**
 * 
 */
package com.group.module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author prashant.mishra1
 *
 */
public class ApiException extends ResponseStatusException {
	
	


	public ApiException(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
	}

	public ApiException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public ApiException(HttpStatus status) {
		super(status);
	}
	
	public ApiException(HttpStatus status, String reason, Object ...placeHolders) {
		super(status,reason);
		this.placeHolderValues = placeHolders;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8558685123764267891L;
	
	private Object[] placeHolderValues;

	public Object[] getPlaceHolderValues() {
		return placeHolderValues;
	}


}
