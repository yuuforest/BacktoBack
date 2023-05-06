package com.backtoback.media.video.service;

import com.google.gson.JsonObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public interface VideoService {


  void startTodayGame();

  void makeAllVideoRoom();

  void enterVideoRoom(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject);

  void closeVideoRoom(Long gameSeq);

  void onIceCandidate(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject) throws Exception;
}
