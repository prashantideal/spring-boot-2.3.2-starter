/**
 * 
 */
package com.group.module.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.group.module.common.ApiResponse;
import com.group.module.common.GenericResponse;
import com.group.module.config.AuthTokenFilter;
import com.group.module.exception.ApiException;
import com.group.module.exception.Codes;
import com.group.module.exception.UserException;
import com.group.module.model.LoginRequest;
import com.group.module.model.Platform;
import com.group.module.service.LoginService;

/**
 * @author prashant.mishra1
 *
 */
@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;


	@PostMapping("/login/init/{user}")
	public ApiResponse<String> initLogin(@PathVariable("user") String username) {
		try {
			loginService.initLogin(username);
		}catch(UserException ue) {
			ue.printStackTrace();
			return GenericResponse.createErrorResponse(ue.getMessage());
		}
		catch(Exception e) {
			e.printStackTrace();
			return GenericResponse.createErrorResponse("5555");
			
		}
		return GenericResponse.createSuccessResponse("Success");
	}
	
	@PostMapping("/login/attempt")
	public ApiResponse<String> login(@RequestBody @Valid LoginRequest loginReq, @RequestHeader("platform") Platform platform) {
		try {
			loginReq.setPlatform(platform);
			String userToken = loginService.login(loginReq);
			return GenericResponse.createSuccessResponse(userToken);
			
		}catch(BadCredentialsException | UserException e) {
			e.printStackTrace();
			throw new ApiException(HttpStatus.UNAUTHORIZED,Codes.USER_INVALID_CREDS);
		}
	}
	
	
	@PostMapping("/logout")
	public void logout(HttpServletRequest request) {
		String userToken = AuthTokenFilter.parseToken(request);
		if (!loginService.clearSession(userToken))
			throw new ApiException(HttpStatus.BAD_REQUEST, Codes.USER_NOT_FOUND);
			
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse<Error> handleException(MethodArgumentNotValidException ex) {
		return GenericResponse.createErrorResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}
}
