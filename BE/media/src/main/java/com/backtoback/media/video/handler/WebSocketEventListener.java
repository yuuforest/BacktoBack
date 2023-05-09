package com.backtoback.media.video.handler;


import com.backtoback.media.video.domain.Participant;
import com.backtoback.media.video.domain.VideoRoom;
import com.backtoback.media.video.repository.ParticipantRepository;
import com.backtoback.media.video.repository.VideoRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.KurentoClient;
import org.kurento.client.WebRtcEndpoint;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketEventListener {

  private final KurentoClient kurento;

  private final VideoRoomRepository videoRoomRepository;

  private final ParticipantRepository participantRepository;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {

    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    MessageHeaderAccessor messageHeaderAccessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);
    GenericMessage generic = (GenericMessage) messageHeaderAccessor.getHeader("simpConnectMessage");
    Map nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");

    String gameSeq = (String)((List) nativeHeaders.get("gameId")).get(0);
    String userId = (String)((List) nativeHeaders.get("userId")).get(0);
    String sessionId = headerAccessor.getSessionId();

    VideoRoom videoRoom = videoRoomRepository.findById(gameSeq).orElseThrow();

    Participant participant = new Participant();
    participant.setId(sessionId);
    participant.setUserId(userId);
    participant.setGameSeq(gameSeq);

    participantRepository.save(participant);
    videoRoomRepository.save(videoRoom);
  }


  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String sessionId = headerAccessor.getSessionId();

    if(participantRepository.findById(sessionId).isPresent()){
      Participant participant = participantRepository.findById(sessionId).get();
      String webRtcEndpointId = participant.getWebRtcEndpointId();
      WebRtcEndpoint webRtcEndpoint = kurento.getById(webRtcEndpointId,WebRtcEndpoint.class);

      if(webRtcEndpoint!=null){
        webRtcEndpoint.release();
      }

      participantRepository.deleteById(sessionId);
    }

  }

}
