package com.keybank.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class CompanyProfileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyProfileServiceApplication.class, args);
	}

}
