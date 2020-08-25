/**
 * 
 */
package com.group.module.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * @author prashant.mishra1
 *
 */
@Document("otp_txns")
@Data
public class Otp extends BaseDocument{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3120632654540474673L;

	public enum Type {LOGIN}

	@Field
	private String value;
	
	@Field
	private Type type;
	
	@Field
	private LocalDateTime validTill;
	
	@Field
	private Boolean active = true;
	
	@Field
	private String userId;
	
	@Field
	private Boolean consumed = false;
}
