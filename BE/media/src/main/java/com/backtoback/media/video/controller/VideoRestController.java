package com.backtoback.media.video.controller;


import com.backtoback.media.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VideoRestController {

  private final VideoService videoService;

  @GetMapping("/makeRoom")
  public void makeRoom(){
    videoService.makeAllVideoRoom();
  }
}
