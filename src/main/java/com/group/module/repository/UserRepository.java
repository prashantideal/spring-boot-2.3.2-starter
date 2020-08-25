/**
 * 
 */
package com.group.module.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.group.module.model.User;

/**
 * @author prashant.mishra1
 *
 */
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByMobile(String mobile);
}
