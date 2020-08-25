/**
 * 
 */
package com.group.module.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.UUID;

import org.springframework.util.StringUtils;

/**
 * @author prashant.mishra1
 *
 */
public class SessionToken {
	
	public static final String elementSeparator = ":";
	
	private String userId;
	
	private String deviceId;
	
	private String platform;
	
	private String sessionId;

	public SessionToken(String userId,String deviceId,Platform platform) {
		this.deviceId = generateHash(deviceId);
		this.userId = generateHash(userId);
		this.platform = generateHash(platform.name());
		this.sessionId = generateHash(UUID.randomUUID().toString() + "- "+  Instant.now().toEpochMilli());
	}
	
	public SessionToken(String userId,Platform platform) {
		this.userId = generateHash(userId);
		this.platform = generateHash(platform.name());
		this.sessionId = generateHash(UUID.randomUUID().toString() + "- "+  Instant.now().toEpochMilli());
	}
	
	
	public SessionToken(String userToken) {
		String[] tokenParts = userToken.split(elementSeparator);
		if(StringUtils.hasText(deviceId))
			handleWithDeviceId(tokenParts);
		else
			handleWithOutDeviceId(tokenParts);
	}
	
	private void handleWithDeviceId(String[] tokenParts) {
		if(tokenParts == null || tokenParts.length != 4)
			throw new IllegalArgumentException("Invalid user token");
		
		this.userId = tokenParts[0];
		this.deviceId = tokenParts[1];
		this.platform = tokenParts[2];
		this.sessionId = tokenParts[3];
	}
	
	private void handleWithOutDeviceId(String[] tokenParts) {
		if(tokenParts == null || tokenParts.length != 3)
			throw new IllegalArgumentException("Invalid user token");
		
		this.userId = tokenParts[0];
		this.platform = tokenParts[1];
		this.sessionId = tokenParts[2];
	}
	
	public String toUserToken() {
		
		return (userId + elementSeparator + (StringUtils.hasText(deviceId)? (deviceId + elementSeparator): "") + platform + elementSeparator + sessionId);
		
	}
	
	public String toStoredToken() {
		return (userId + elementSeparator +  (StringUtils.hasText(deviceId)? (deviceId + elementSeparator): "") + platform + elementSeparator + generateHash(sessionId));
	}
	
	private String generateHash(String source){
		return getSHA512(source);
	}
	
	
	private String getSHA512(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}
}
