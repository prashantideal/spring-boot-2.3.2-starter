/**
 * 
 */
package com.group.module.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * @author prashant.mishra1
 *
 */
@ConfigurationProperties
@Configuration
@PropertySource("classpath:secure.properties")
@Data
public class SecuredConfigurations {
	
	private AuthConfig auth;

	@Data
	public static class AuthConfig{
		
		private String secretKey = null;
	}

}
