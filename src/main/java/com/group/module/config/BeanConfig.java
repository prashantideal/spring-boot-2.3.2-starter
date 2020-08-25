/**
 * 
 */
package com.group.module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.group.module.repository.AuthTokenRepository;
import com.group.module.service.LoginService;
import com.group.module.service.OtpService;
import com.group.module.service.UserManagerService;
import com.group.module.service.impl.LoginServiceImpl;
import com.group.module.service.impl.OtpServiceImpl;
import com.group.module.service.impl.UserManagerServiceImpl;

/**
 * @author prashant.mishra1
 *
 */
@Configuration("module.beanConfig")
public class BeanConfig {
	
	
	@Bean
	public LoginService loginService() {
		return new LoginServiceImpl();
	}
	
	@Bean
	public UserManagerService userService() {
		return new UserManagerServiceImpl();
	}
	
	@Bean
	public OtpService otpService() {
		return new OtpServiceImpl();
	}
	
	@Bean
	public AuthTokenRepository authTokenRepository() {
		return new AuthTokenRepository();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
