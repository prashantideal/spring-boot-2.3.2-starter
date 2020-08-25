/**
 * 
 */
package com.group.module.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.group.module.common.AuthSuccessHandler;
import com.group.module.common.NoPasswordEncoder;

/**
 * @author prashant.mishra1
 *
 */
@Configuration
public class BaseSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	@Qualifier("customSpringFirewall")
	private HttpFirewall customFireWall;
	
	@Bean
	public AuthEntryPoint authEntryPoint() {
		return new AuthEntryPoint();
	}
	
	
	@Bean
	public AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.httpFirewall(customFireWall);
	}


	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
		.and()
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authEntryPoint())
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.logout().disable()
			.authorizeRequests().antMatchers("/login/**", "/actuator/health").permitAll()
			.anyRequest().authenticated();
		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}
	
	@Bean("customSpringFirewall")
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);
	    firewall.setAllowSemicolon(true);
	    return firewall;
	}
	
	@Bean
	public PasswordEncoder otpEncoder() {
	    PasswordEncoder encoder = new NoPasswordEncoder();
	    return encoder;
	}
	
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new AuthSuccessHandler();
	}

}
