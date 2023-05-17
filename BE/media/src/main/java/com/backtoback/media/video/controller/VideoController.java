package com.backtoback.media.video.controller;


import com.backtoback.media.video.service.VideoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VideoController {

  private final VideoService videoService;
  private final SimpMessagingTemplate template;
  private final Gson gson = new GsonBuilder().create();

  @MessageMapping("video/start")
  public void start(StompHeaderAccessor stompHeaderAccessor,@Payload String message){
    JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
    videoService.enterVideoRoom(stompHeaderAccessor,jsonMessage);
  }

  @MessageMapping("video/stop")
  public void stop(@Payload String message){

  }

  @MessageMapping("video/resume")
  public void resume(@Payload String message){

  }

  @MessageMapping("video/onIceCandidate")
  public void onIceCandidate(StompHeaderAccessor stompHeaderAccessor,@Payload String message) throws Exception{
    JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
    videoService.onIceCandidate(stompHeaderAccessor,jsonMessage);
  }

}
