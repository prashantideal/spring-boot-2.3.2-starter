/**
 * 
 */
package com.group.module.exception;

import org.springframework.util.StringUtils;

/**
 * @author prashant.mishra1
 *
 */
public class UserException extends Exception {
	
	private String code;

	/**
	 * 
	 */
	private static final long serialVersionUID = -174067422673162575L;
	
	public UserException(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		if(StringUtils.hasLength(code))
			return code;
		return super.getMessage();
	}
	
	

}
