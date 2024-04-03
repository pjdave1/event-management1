package com.example.eventmanagement.demo.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 


@EnableWebSecurity
@SpringBootApplication
@EntityScan(basePackages = {"entity"}) 
public class Application  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	

}
