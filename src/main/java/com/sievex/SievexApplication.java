package com.sievex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// @EnableScheduling // Enable scheduling for periodic job execution
public class SievexApplication {

	public static void main(String[] args) {
		SpringApplication.run(SievexApplication.class, args);
	}

}
