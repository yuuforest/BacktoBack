package com.backtoback.business.gamemanager.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "point-service", url = "http://k8a708.p.ssafy.io:8084")
public interface PointServiceClient {

  @PostMapping("/api/point/betting/start")
  public void readyToStartBetting();
}