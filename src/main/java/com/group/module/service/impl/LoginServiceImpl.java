/**
 * 
 */
package com.group.module.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.group.module.exception.Codes;
import com.group.module.exception.UserException;
import com.group.module.model.LoginRequest;
import com.group.module.model.Otp;
import com.group.module.model.User;
import com.group.module.model.Otp.Type;
import com.group.module.repository.AuthTokenRepository;
import com.group.module.repository.UserRepository;
import com.group.module.service.LoginService;
import com.group.module.service.OtpService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author prashant.mishra1
 *
 */
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthTokenRepository authTokenRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void initLogin(String username) throws UserException {
		sendOtp(username);
	}

	private void sendOtp(String username) throws UserException {

		Otp otp = null;

		Optional<User> user = repository.findByMobile(username);

		if (!user.isPresent()) {
			throw new UserException(Codes.USER_NOT_FOUND);
		}

		if (user.get().getDeleted()) {
			throw new UserException(Codes.USER_DELETED);
		}

		Optional<Otp> existingActive = otpService.getUnusedOTP(Type.LOGIN, user.get().getId());
		otp = existingActive.orElse(null);

		if (!existingActive.isPresent()) {
			otp = otpService.generateNew(Type.LOGIN, user.get().getId());
		}

		sendSms(otp.getValue());
	}

	public String sendSms(String otp) {

		String response = null;
		try {

			// Dummy OTP delivery through mailtrap, implement API of your provider

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("noreply@pouch.com");
			message.setTo("dummy@gmail.com");
			message.setSubject("Your OTP is : " + otp);
			message.setText(otp);
			mailSender.send(message);

		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
		return response;
	}

	@Override
	public String login(LoginRequest loginReq) throws UserException {
		Authentication auth = authManager.authenticate(loginReq.toAuth());
		otpService.markConsumed(((User) auth.getPrincipal()).getOtp());
		SecurityContextHolder.getContext().setAuthentication(auth);
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		String userToken = authTokenRepository.createSession(user, loginReq.getPlatform());
		log.debug("{} and {}", user.toString(), userToken);
		return userToken;
	}

	@Override
	public Boolean clearSession(String userToken) {
		SecurityContextHolder.clearContext();
		return authTokenRepository.clearSession(userToken);
	}

}
