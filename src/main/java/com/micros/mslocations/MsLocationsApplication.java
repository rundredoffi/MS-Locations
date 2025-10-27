package com.micros.mslocations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.micros.mslocations")
public class MsLocationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLocationsApplication.class, args);
	}

}
