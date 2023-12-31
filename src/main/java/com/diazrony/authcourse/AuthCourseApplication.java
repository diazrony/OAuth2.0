package com.diazrony.authcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin("*")
@EnableAutoConfiguration
@EnableConfigurationProperties
@SpringBootApplication
public class AuthCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthCourseApplication.class, args);
	}

}
