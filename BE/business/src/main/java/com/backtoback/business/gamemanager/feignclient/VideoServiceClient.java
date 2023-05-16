package com.backtoback.business.gamemanager.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "video-service", url = "http://localhost:8080")
public interface VideoServiceClient {

	@GetMapping("/makeRoom")
	void makeRoom();

}
