package com.backtoback.media.video.service;

import com.backtoback.media.video.dto.HighLightMessageDto;
import com.google.gson.JsonObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

public interface VideoService {


  void startTodayGame();

  void makeAllVideoRoom();

  void enterVideoRoom(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject);

  void closeVideoRoom(Long gameSeq);

  void makeHighLight(HighLightMessageDto highLightMessageDto) throws IOException, InterruptedException;

  void deleteHighLight(Long gameSeq);

  void sendHighLight(Long gameSeq);

  void onIceCandidate(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject) throws Exception;
}
