package com.backtoback.business.gamemanager.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "video-service", url = "http://k8a708.p.ssafy.io:8091")
public interface VideoServiceClient {

  @GetMapping("/api/media/makeRoom")
  void makeRoom();

}
