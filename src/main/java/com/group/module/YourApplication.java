package com.group.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableMongoRepositories("com.group.module*.repository")
@EnableMongoAuditing
@EnableGlobalMethodSecurity(securedEnabled=true,jsr250Enabled = true)
public class YourApplication {

	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}
	
}
