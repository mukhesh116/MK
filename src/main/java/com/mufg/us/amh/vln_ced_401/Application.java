package com.mufg.us.amh.vln_ced_401;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
    	log.info("Mufg transformation service started.");
        SpringApplication.run(Application.class, args);
    }

}