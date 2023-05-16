package com.backtoback.business.gamemanager.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "betting-service", url = "http://localhost:8084")
public interface BettingServiceClient {

	@GetMapping("/makeRoom")
	void makeRoom();

}
