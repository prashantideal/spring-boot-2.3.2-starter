/**
 * 
 */
package com.group.module.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.group.module.model.Platform;
import com.group.module.model.SessionInfo;
import com.group.module.model.SessionToken;
import com.group.module.model.User;

/**
 * @author prashant.mishra1
 *
 */
public class AuthTokenRepository {
	
	public static final String sessionCacheName = "session";
	
	public static final String sessionDBIndex = "1";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public String createSession(User user, Platform platform) {
		SessionToken token = new SessionToken(user.getId(),platform);
		SessionInfo sessionInfo = new SessionInfo(user);
		redisTemplate.opsForValue().set(redisKeyFor(token), sessionInfo);
		return token.toUserToken();
	}
	
	
	public SessionInfo findSession(String userToken) {
		SessionToken token = new SessionToken(userToken);
		SessionInfo sessionInfo = (SessionInfo) redisTemplate.opsForValue().get(redisKeyFor(token));
		return sessionInfo;
	}
	
	public Boolean clearSession(String userToken) {
		SessionToken token = new SessionToken(userToken);
		return redisTemplate.delete(redisKeyFor(token));
	}
	
	private String redisKeyFor(SessionToken token) {
		return sessionCacheName+ SessionToken.elementSeparator+ sessionDBIndex + SessionToken.elementSeparator +token.toStoredToken();
	}

}
