/**
 * 
 */
package com.group.module.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.group.module.model.Otp;
import com.group.module.model.Otp.Type;

/**
 * @author prashant.mishra1
 *
 */
public interface OtpRepository extends MongoRepository<Otp, String>{

	@Query(value="{ 'userId' : ?0, 'type': ?1, active: true, consumed: false }")
	public Optional<Otp> findActiveForUserAndType(String userId, Type type);
}
