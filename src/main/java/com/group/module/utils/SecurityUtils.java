/**
 * 
 */
package com.group.module.utils;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

import com.group.module.model.User;

/**
 * @author prashant.mishra1
 *
 */
public class SecurityUtils {

	public static Optional<User> getLoggedInUser(){
		return Optional.ofNullable((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()); 
	}
}
