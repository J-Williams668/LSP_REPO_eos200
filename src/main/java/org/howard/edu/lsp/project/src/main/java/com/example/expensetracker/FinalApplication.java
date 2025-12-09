package com.example.expensetracker;

import com.example.expensetracker.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class FinalApplication {

	public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
	}

}
