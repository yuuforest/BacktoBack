package com.backtoback.cheer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CheerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheerApplication.class, args);
	}

}
