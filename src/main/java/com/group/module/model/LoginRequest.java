/**
 * 
 */
package com.group.module.model;

import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import lombok.Data;

/**
 * @author prashant.mishra1
 *
 */
@Data
public class LoginRequest {
	
	@NotNull(message="4000")
	private String username;
	
	@NotNull(message="4002")
	private String otp;
	
	private Platform platform;
	
	public Authentication toAuth() {
		return new UsernamePasswordAuthenticationToken(username, otp);
	}

}
