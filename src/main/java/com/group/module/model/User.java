package com.group.module.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document("users")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User extends BaseDocument implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000148750851023125L;
	

	@Field(name="first_name")
	private String firstName;

	@Field(name="last_name")
	private String lastName;

	private String email;
	
	private String mobile;

	@Field(name="last_login")
	private Timestamp lastLogin;

	@Getter(onMethod = @__(@JsonProperty("is_active")))
	@Setter
	@Field(name="is_active")
	private boolean isActive=true;

	@Transient
	@JsonIgnore
	private Otp otp;

	@JsonIgnore
	@Field(name="failed_logins")
	private int failedLogins;
	
	private List<String> roles;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(roles!=null && !roles.isEmpty())
			return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		return Collections.emptyList();
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return getMobile();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return isActive();
	}

	@Override
	public String getPassword() {
		return this.otp.getValue();
	}
}
