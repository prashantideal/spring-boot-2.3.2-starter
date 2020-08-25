/**
 * 
 */
package com.group.module.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.group.module.common.CustomErrorAttributes;

/**
 * @author prashant.mishra1
 *
 */
@Configuration
public class SessionConfig {

		
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:errors/exception-message");
	    messageSource.setCacheSeconds(10); //reload messages every 10 seconds
	    messageSource.setUseCodeAsDefaultMessage(true);
	    return messageSource;
	}
	
	
	@Bean
	public CustomErrorAttributes errorAttributes() {
		return new CustomErrorAttributes();
	}
	
}
