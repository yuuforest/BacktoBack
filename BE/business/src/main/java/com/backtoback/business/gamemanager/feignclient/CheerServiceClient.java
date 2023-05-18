package com.backtoback.business.gamemanager.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cheer-service", url = "http://k8a708.p.ssafy.io:8089")
public interface CheerServiceClient {
  @PostMapping("/api/cheer/start")
  public void readyToStartCheer();
}
