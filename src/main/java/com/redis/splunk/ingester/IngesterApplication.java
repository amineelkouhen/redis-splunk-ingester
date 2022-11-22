package com.redis.splunk.ingester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class IngesterApplication {
	public static void main(String[] args) {
		SpringApplication.run(IngesterApplication.class, args);
	}
}
