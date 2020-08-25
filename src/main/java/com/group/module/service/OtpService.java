package com.group.module.service;

import java.util.Optional;

import com.group.module.model.Otp;
import com.group.module.model.Otp.Type;

public interface OtpService {

	public Otp generateNew(Type type, String user);
	
	public Otp markConsumed(Otp otp);
	
	public Optional<Otp> getUnusedOTP(Type type, String user);
}
