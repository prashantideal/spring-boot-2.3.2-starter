package com.group.module.service;

import com.group.module.exception.UserException;
import com.group.module.model.LoginRequest;

public interface LoginService {

	public void initLogin(String username) throws UserException;
	
	public String login(LoginRequest loginReq) throws UserException;
	
	public Boolean clearSession(String userToken);
}
