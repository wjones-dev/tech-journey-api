package com.techjourney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechJourneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechJourneyApiApplication.class, args);
	}

}
