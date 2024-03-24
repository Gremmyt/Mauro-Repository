package com.practice.msvcbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsvcBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcBookApplication.class, args);
	}

}
