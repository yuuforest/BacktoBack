package com.backtoback.media.video.service;

import com.backtoback.media.game.domain.Game;
import com.backtoback.media.game.domain.GameActiveType;
import com.backtoback.media.game.repository.GameRepository;
import com.backtoback.media.video.domain.Participant;
import com.backtoback.media.video.domain.VideoRoom;
import com.backtoback.media.video.dto.MessageDto;
import com.backtoback.media.video.repository.ParticipantRepository;
import com.backtoback.media.video.repository.VideoRoomRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

  private final Gson gson = new GsonBuilder().create();

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final KurentoClient kurento;

  private final VideoRoomRepository videoRoomRepository;

  private final GameRepository gameRepository;

  private final ParticipantRepository participantRepository;

  private final StreamBridge streamBridge;

  private final String VIDEO_URL = "https://raw.githubusercontent.com/Kurento/test-files/main/video/format/sintel.webm";

  // 방 접속
  @Override
  public void enterVideoRoom(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject) {
    String sessionId = stompHeaderAccessor.getSessionId();
    String gameId = jsonObject.get("gameId").getAsString();
    String userId = jsonObject.get("userId").getAsString();

    VideoRoom videoRoom = videoRoomRepository.findById(gameId).orElseThrow();
    MediaPipeline mediaPipeline = kurento.getById(videoRoom.getMediaPipelineId(), MediaPipeline.class);
    PlayerEndpoint playerEndpoint = kurento.getById(videoRoom.getPlayerEndpointId(), PlayerEndpoint.class);
    WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(mediaPipeline).build();

    Participant participant = participantRepository.findById(sessionId).orElseThrow();
    participant.setWebRtcEndpointId(webRtcEndpoint.getId());

    participantRepository.save(participant);
    playerEndpoint.connect(webRtcEndpoint);

    webRtcEndpoint.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
      @Override
      public void onEvent(IceCandidateFoundEvent event) {
        JsonObject response = new JsonObject();
        response.addProperty("id", "iceCandidate");
        response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
        sendStompMessage(gameId, userId, response.toString());
      }
    });

    String sdpOffer = jsonObject.get("sdpOffer").getAsString();
    String sdpAnswer = webRtcEndpoint.processOffer(sdpOffer);

    log.info("[Handler::start] SDP Offer from browser to KMS:\n{}", sdpOffer);
    log.info("[Handler::start] SDP Answer from KMS to browser:\n{}", sdpAnswer);

    JsonObject response = new JsonObject();
    response.addProperty("id", "startResponse");
    response.addProperty("sdpAnswer", sdpAnswer);
    sendStompMessage(gameId, userId, response.toString());

    webRtcEndpoint.addMediaStateChangedListener(new EventListener<MediaStateChangedEvent>() {
      @Override
      public void onEvent(MediaStateChangedEvent event) {
        if (event.getNewState() == MediaState.CONNECTED) {
          PlayerEndpoint playerEndpoint = kurento.getById(videoRoom.getPlayerEndpointId(), PlayerEndpoint.class);
          VideoInfo videoInfo = playerEndpoint.getVideoInfo();
          JsonObject response = new JsonObject();
          response.addProperty("id", "videoInfo");
          sendStompMessage(gameId, userId, response.toString());
        }
      }
    });

    webRtcEndpoint.gatherCandidates();

  }


  //경기 시작
  @Override
  public void startTodayGame() {
    List<Game> gameList = gameRepository.getAllTodayGame();

    for (Game game : gameList) {
      startGame(game.getGameSeq());
    }
  }

  //경기 시작 kafka produce
  public void startGame(Long gameSeq) {
    startVideo(gameSeq);
    streamBridge.send("producer-out-0", new MessageDto(gameSeq, GameActiveType.IN_GAME));
  }

  //경기 끝 kafka produce
  public void endGame(Long gameSeq) {
    streamBridge.send("producer-out-0", new MessageDto(gameSeq, GameActiveType.AFTER_GAME));
  }

  //비디오 시작
  public void startVideo(Long gameSeq) {
    VideoRoom videoRoom = videoRoomRepository.findById(gameSeq.toString()).orElseThrow();
    PlayerEndpoint playerEndpoint = kurento.getById(videoRoom.getPlayerEndpointId(), PlayerEndpoint.class);
    playerEndpoint.play();
  }

  // 자정 지난 시점에 경기 생성
  @Override
  public void makeAllVideoRoom() {
    List<Game> gameList = gameRepository.getAllTodayGame();

    for (Game game : gameList) {
      makeVideoRoom(game.getGameSeq(), VIDEO_URL);
    }
  }

  //비디오 방 만들기
  public void makeVideoRoom(Long gameSeq, String videoUrl) {
    VideoRoom videoRoom = new VideoRoom();
    MediaPipeline mediaPipeline = kurento.createMediaPipeline();
    PlayerEndpoint playerEndpoint = new PlayerEndpoint.Builder(mediaPipeline, videoUrl).build();
    videoRoom.setId(gameSeq.toString());
    videoRoom.setMediaPipelineId(mediaPipeline.getId());
    videoRoom.setPlayerEndpointId(playerEndpoint.getId());
    videoRoomRepository.save(videoRoom);

    playerEndpoint.addErrorListener(new EventListener<ErrorEvent>() {
      @Override
      public void onEvent(ErrorEvent event) {
        log.info("ErrorEvent: {}", event.getDescription());
      }
    });

    playerEndpoint.addEndOfStreamListener(new EventListener<EndOfStreamEvent>() {
      @Override
      public void onEvent(EndOfStreamEvent event) {
        log.info("EndOfStreamEvent: {}", event.getTimestampMillis());

        endGame(gameSeq);
        closeVideoRoom(gameSeq);
      }
    });
  }

  //비디오 방 삭제
  @Override
  public void closeVideoRoom(Long gameSeq) {
    log.info("비디오 room 삭제");
    VideoRoom videoRoom = videoRoomRepository.findById(gameSeq.toString()).orElseThrow();
    MediaPipeline mediaPipeline = kurento.getById(videoRoom.getMediaPipelineId(), MediaPipeline.class);
    mediaPipeline.release();
    deleteParticipants(gameSeq);
    videoRoomRepository.deleteById(gameSeq.toString());
  }

  //방에 있는 참여자 Redis 제거
  public void deleteParticipants(Long gameSeq) {
    VideoRoom videoRoom = videoRoomRepository.findById(gameSeq.toString()).orElseThrow();
    participantRepository.findAll().forEach((participant -> {
      if (participant.getGameSeq().equals(gameSeq.toString())) {
        participantRepository.deleteById(participant.getId());
      }
    }));
  }

  //webrtc 후보자 찾아주기 => IP,PORT 찾기
  @Override
  public void onIceCandidate(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject) throws Exception {
    String sessionId = stompHeaderAccessor.getSessionId();
    JsonObject jsonCandidate = jsonObject.get("candidate").getAsJsonObject();
    WebRtcEndpoint webRtcEndpoint;
    Participant participant;
    Optional<Participant> participantOptional = participantRepository.findById(sessionId);

    //동기화
    while (!participantOptional.isPresent() || participantOptional.get().getWebRtcEndpointId() == null) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new Exception("error occurred");
      }
      participantOptional = participantRepository.findById(sessionId);
    }

    participant = participantOptional.get();
    webRtcEndpoint = kurento.getById(participant.getWebRtcEndpointId(), WebRtcEndpoint.class);

    IceCandidate candidate =
            new IceCandidate(jsonCandidate.get("candidate").getAsString(), jsonCandidate
                    .get("sdpMid").getAsString(), jsonCandidate.get("sdpMLineIndex").getAsInt());

    webRtcEndpoint.addIceCandidate(candidate);

  }

  //stompMessage 보내주기
  public synchronized void sendStompMessage(String gameId, String userId, String message) {
    simpMessagingTemplate.convertAndSendToUser(userId, "/sub/" + gameId, message);
  }


}
