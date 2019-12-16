package org.mvc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
@EnableAutoConfiguration
public class SpringBatchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemoApplication.class, args);
	}

}
