package com.backtoback.business.gamemanager.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "betting-service", url = "http://k8a708.p.ssafy.io:8084")
public interface BettingServiceClient {

  @PostMapping("/api/betting/start")
  public ResponseEntity<?> readyToStartBetting();
}