/**
 * 
 */
package com.group.module.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.group.module.model.Otp;
import com.group.module.model.Otp.Type;
import com.group.module.repository.OtpRepository;
import com.group.module.service.OtpService;

/**
 * @author prashant.mishra1
 *
 */
@Transactional(readOnly=false)
public class OtpServiceImpl implements OtpService {
	
	private static final Integer otpValidity = 5; // in minutes
	
	private PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private OtpRepository repository;
	
	@Autowired
	private PasswordEncoder otpEncoder;
	
	public Otp generateNew(Type type, String user) {
		Otp otp = new Otp();
		otp.setValue(otpEncoder.encode(String.format("%06d", generateOTP())));
		otp.setUserId(user);
		otp.setType(type);
		otp.setValidTill(LocalDateTime.now().plus(Duration.of(otpValidity,ChronoUnit.MINUTES)));
		return repository.save(otp);
	}

	private int generateOTP() {
		Random rand = new Random();
		int code = rand.nextInt(899999);
		code += 100000;
		return code;
	}

	@Override
	public Otp markConsumed(Otp otp) {
		otp.setActive(false);
		otp.setConsumed(true);
		otp.setValue(bcryptEncoder.encode(otp.getValue()));
		return repository.save(otp);
	}

	@Override
	public Optional<Otp> getUnusedOTP(Type type, String userId) {
		Optional<Otp> otp = repository.findActiveForUserAndType(userId, type);
		if(otp.isPresent()) {
//			Long difference = Duration.between(otp.get().getValidTill(), LocalDateTime.now()).get(ChronoUnit.SECONDS);
			Long difference = ChronoUnit.MINUTES.between(otp.get().getValidTill(), LocalDateTime.now());
			if(difference < otpValidity) {
				return otp;
			}			
			markConsumed(otp.get());
		}
		return Optional.empty();
	}
}
