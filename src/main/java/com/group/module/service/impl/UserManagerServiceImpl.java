/**
 * 
 */
package com.group.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.group.module.exception.Codes;
import com.group.module.model.Otp;
import com.group.module.model.User;
import com.group.module.model.Otp.Type;
import com.group.module.repository.OtpRepository;
import com.group.module.repository.UserRepository;
import com.group.module.service.UserManagerService;

/**
 * @author prashant.mishra1
 *
 */
public class UserManagerServiceImpl implements UserManagerService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private OtpRepository otpRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = repository.findByMobile(username).orElseThrow(() -> new UsernameNotFoundException(Codes.USER_NOT_FOUND));
			Otp otp = otpRepository.findActiveForUserAndType(user.getId(), Type.LOGIN)
					.orElseThrow(() -> new UsernameNotFoundException(Codes.USER_NOT_FOUND));
			user.setOtp(otp);
			return user;
		} catch (Exception e) {
			throw e;
		}

	}

}
