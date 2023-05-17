package com.backtoback.media.video.service;

import com.backtoback.media.game.domain.Game;
import com.backtoback.media.game.domain.GameActiveType;
import com.backtoback.media.game.repository.GameRepository;
import com.backtoback.media.video.domain.Participant;
import com.backtoback.media.video.domain.Record;
import com.backtoback.media.video.domain.VideoRoom;
import com.backtoback.media.video.dto.HighLightMessageDto;
import com.backtoback.media.video.dto.HighLightPosition;
import com.backtoback.media.video.dto.MessageDto;
import com.backtoback.media.video.feignclient.FlaskServiceClient;
import com.backtoback.media.video.repository.HighLightRepository;
import com.backtoback.media.video.repository.ParticipantRepository;
import com.backtoback.media.video.repository.RecordRepository;
import com.backtoback.media.video.repository.VideoRoomRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.kurento.client.*;
import org.kurento.client.EventListener;
import org.kurento.jsonrpc.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

  private final FlaskServiceClient flaskServiceClient;

  private final Gson gson = new GsonBuilder().create();

  private final SimpMessagingTemplate simpMessagingTemplate;

  private final KurentoClient kurento;

  private final VideoRoomRepository videoRoomRepository;

  private final GameRepository gameRepository;

  private final ParticipantRepository participantRepository;

  private final HighLightRepository highLightRepository;

  private final RecordRepository recordRepository;

  private final StreamBridge streamBridge;

  private final MpegService mpegService;

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

    long mediaStartTime = startVideo(gameSeq);
    log.info("mediaStartTime 입니다"+mediaStartTime);
    streamBridge.send("producer-out-0", new MessageDto(gameSeq, GameActiveType.IN_GAME,mediaStartTime));
  }

  //경기 끝 kafka produce
  public void endGame(Long gameSeq) {

    long mediaEndTime = System.currentTimeMillis();
    log.info("mediaEndTime 입니다"+mediaEndTime);
    streamBridge.send("producer-out-0", new MessageDto(gameSeq, GameActiveType.AFTER_GAME,mediaEndTime));
  }

  //비디오 시작
  public long startVideo(Long gameSeq) {

    VideoRoom videoRoom = videoRoomRepository.findById(gameSeq.toString()).orElseThrow();
    PlayerEndpoint playerEndpoint = kurento.getById(videoRoom.getPlayerEndpointId(), PlayerEndpoint.class);
    RecorderEndpoint recorderEndpoint = kurento.getById(videoRoom.getRecordEndpointId(),RecorderEndpoint.class);
    recorderEndpoint.record();
    playerEndpoint.play();

    return System.currentTimeMillis();
  }

  // 자정 지난 시점에 경기 생성
  @Override
  public void makeAllVideoRoom() {
    List<Game> gameList = gameRepository.getAllTodayGame();
    for (Game game : gameList) {
      makeVideoRoom(game.getGameSeq(), game.getGameUrl());
    }
  }

  //비디오 방 만들기
  public void makeVideoRoom(Long gameSeq, String videoUrl) {
//    final String RECORDER_FILE_PATH = "/tmp/" + UUID.randomUUID().toString() +".webm";
    final String RECORDER_FILE_PATH = "/record/" + UUID.randomUUID().toString() +".webm";
    VideoRoom videoRoom = new VideoRoom();
    MediaPipeline mediaPipeline = kurento.createMediaPipeline();
    PlayerEndpoint playerEndpoint = new PlayerEndpoint.Builder(mediaPipeline, videoUrl).build();
    MediaProfileSpecType profile = MediaProfileSpecType.WEBM;
    RecorderEndpoint recorderEndpoint = new RecorderEndpoint.Builder(mediaPipeline,"file://"+RECORDER_FILE_PATH).withMediaProfile(profile).build();
    playerEndpoint.connect(recorderEndpoint);
    Record record = new Record();
    record.setId(gameSeq.toString());
    record.setRecordPath(RECORDER_FILE_PATH);
    recordRepository.save(record);

    videoRoom.setId(gameSeq.toString());
    videoRoom.setMediaPipelineId(mediaPipeline.getId());
    videoRoom.setPlayerEndpointId(playerEndpoint.getId());
    videoRoom.setRecordEndpointId(recorderEndpoint.getId());
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
    RecorderEndpoint recorderEndpoint = kurento.getById(videoRoom.getRecordEndpointId(),RecorderEndpoint.class);
    recorderEndpoint.stop();
    mediaPipeline.release();
    deleteParticipants(gameSeq);
    videoRoomRepository.deleteById(gameSeq.toString());

  }

  public void deleteFile(String filePath) {
    try {
      Path path = Paths.get(filePath);
      Files.deleteIfExists(path);
      log.info("파일이 성공적으로 삭제되었습니다.");
    } catch (IOException e) {
      log.info("파일 삭제 중 오류가 발생하였습니다: " + e.getMessage());
      throw new RuntimeException();
    }
  }

  @Override
  public void makeHighLight(HighLightMessageDto highLightMessageDto) throws IOException, InterruptedException {
    log.info("make highlight");
    List<HighLightPosition> highLightPositionList = highLightMessageDto.getHighLightPositionList();
    List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();

    for(HighLightPosition highLightPosition:highLightPositionList){
      CompletableFuture<Void> future = mpegService.createHighLight(highLightMessageDto.getGameSeq(),highLightPosition.getStart(),highLightPosition.getFinish());
      completableFutureList.add(future);
    }

    try {
      for(CompletableFuture<Void> completableFuture:completableFutureList){
          completableFuture.get();
      }
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void deleteHighLight(Long gameSeq){
    log.info("delete record");

    Optional<Record> optionalRecord = recordRepository.findById(gameSeq.toString());

    if(optionalRecord.isPresent()){
      Record record = optionalRecord.get();
      deleteFile(record.getRecordPath());
      recordRepository.deleteById(gameSeq.toString());
    }

    highLightRepository.findAll().forEach(highLight -> {
      log.info("delete highlight");
      if(highLight.getGameSeq().equals(gameSeq.toString())){
        deleteFile(highLight.getHighLightPath());
        highLightRepository.deleteById(gameSeq.toString());
      }
    });
  }

  @Override
  public void sendHighLight(Long gameSeq){
      log.info("send highlight");
      List<MultipartFile> multipartFileList = new ArrayList<>();

      highLightRepository.findAll().forEach((highLight)->{
        if(highLight.getGameSeq().equals(gameSeq.toString())){
            log.info("FILE 추가!!!!!!" + highLight.getHighLightPath());
            File file = new File(highLight.getHighLightPath());
          try {
            MultipartFile multipartFile = convertToMultipartFile(file);
            multipartFileList.add(multipartFile);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      });

      flaskServiceClient.uploadFiles(multipartFileList,gameSeq);
  }

  public MultipartFile convertToMultipartFile(File file) throws IOException {
    DiskFileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());

    InputStream input = new FileInputStream(file);
    OutputStream os = fileItem.getOutputStream();
    IOUtils.copy(input, os);

    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
    return multipartFile;
  }

  public void deleteParticipants(Long gameSeq) {

    participantRepository.findAll().forEach((participant -> {
      if (participant.getGameSeq().equals(gameSeq.toString())) {
        sendPlayEnd(gameSeq.toString(),participant.getUserId());
        participantRepository.deleteById(participant.getId());
      }
    }));

  }

  public void sendPlayEnd(String gameId,String userId){
    JsonObject response = new JsonObject();
    response.addProperty("id", "playEnd");
    sendStompMessage(gameId, userId, response.toString());
  }

  @Override
  public void onIceCandidate(StompHeaderAccessor stompHeaderAccessor, JsonObject jsonObject) throws Exception {
    String sessionId = stompHeaderAccessor.getSessionId();
    JsonObject jsonCandidate = jsonObject.get("candidate").getAsJsonObject();
    WebRtcEndpoint webRtcEndpoint;
    Participant participant;
    Optional<Participant> participantOptional = participantRepository.findById(sessionId);

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


  public synchronized void sendStompMessage(String gameId, String userId, String message) {
    log.info("send stomp message");
    simpMessagingTemplate.convertAndSendToUser(userId, "/sub/" + gameId, message);
  }

}
