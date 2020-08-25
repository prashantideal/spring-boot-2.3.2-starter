/**
 * 
 */
package com.group.module.config;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author prashant.mishra1
 *
 */
@Configuration
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String redisHostName;
	
	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${redis.cluster.mode}")
	private Boolean clusterMode = false;
	
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisQueueTemplate =  new RedisTemplate<String, Object>();
		
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
	    
	    redisQueueTemplate.setConnectionFactory(lettuceConnectionFactory());
	    redisQueueTemplate.setKeySerializer(redisSerializer);
	    redisQueueTemplate.setValueSerializer(valueSerializer());
	    
		return redisQueueTemplate;
	}
	
	@Bean
	public LettuceConnectionFactory lettuceConnectionFactory() {
		
		org.springframework.data.redis.connection.RedisConfiguration config = null;
		if(clusterMode) {
			config = new RedisClusterConfiguration(Arrays.asList(redisHostName+":"+redisPort));
		}else
		{
			config = new RedisStandaloneConfiguration(redisHostName,redisPort);
		}

		
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(config);
		
		return lettuceConnectionFactory;
	}
	
	@Bean
	public GenericJackson2JsonRedisSerializer valueSerializer() {
		ObjectMapper om = new ObjectMapper();
	    om.registerModule(new Jdk8Module());
	    om.registerModule(new JavaTimeModule());
	    PolymorphicTypeValidator pvt = BasicPolymorphicTypeValidator.builder().allowIfBaseType(Serializable.class).build();
	    om.activateDefaultTypingAsProperty(pvt, ObjectMapper.DefaultTyping.NON_FINAL,"@class");
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);
	    return jackson2JsonRedisSerializer;
	}
	
	@Bean
	public RedisCacheManager cacheManager() {
		return RedisCacheManager.create(lettuceConnectionFactory());
	}
}
