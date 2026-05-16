package com.shreyass.irctc_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IrctcCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrctcCloneApplication.class, args);
	}

}
