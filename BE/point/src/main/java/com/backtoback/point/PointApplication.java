package com.backtoback.point;

import com.backtoback.point.betting.dto.request.KafkaReq;
import com.backtoback.point.betting.service.BettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PointApplication {

	@Autowired
	private BettingService bettingService;

	public static void main(String[] args) {
		SpringApplication.run(PointApplication.class, args);
	}

	@Bean
	public Consumer<KafkaReq> process() {
		return KafkaRes -> {
			bettingService.getBettingResult(KafkaRes);	// OK
		};
	}

}
