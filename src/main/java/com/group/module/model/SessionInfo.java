/**
 * 
 */
package com.group.module.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author prashant.mishra1
 *
 */
@Data
public class SessionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4883525605110499852L;
	
	private User user;
	
	private LocalDateTime createdAt;
	
	public SessionInfo() {}
	
	public SessionInfo(User user) {
		this.user = user;
		this.createdAt = LocalDateTime.now();
	}
}
