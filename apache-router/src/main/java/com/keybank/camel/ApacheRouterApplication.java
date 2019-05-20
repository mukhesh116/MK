package com.keybank.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:Beans.xml")
public class ApacheRouterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApacheRouterApplication.class, args);
	}
}