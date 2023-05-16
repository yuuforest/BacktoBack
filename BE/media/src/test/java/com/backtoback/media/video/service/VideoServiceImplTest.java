package com.backtoback.media.video.service;

import com.backtoback.media.video.config.WebSocketConfig;
import com.backtoback.media.video.domain.Record;
import com.backtoback.media.video.dto.HighLightMessageDto;
import com.backtoback.media.video.dto.HighLightPosition;
import com.backtoback.media.video.repository.HighLightRepository;
import com.backtoback.media.video.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

//@SpringBootTest
class VideoServiceImplTest {

//    @Autowired VideoService videoService;
//
//    @Autowired
//    private MpegService mpegService;
//
//    @Autowired
//    private HighLightRepository highLightRepository;
//
//    @Autowired
//    private RecordRepository recordRepository;

    @Test
    void createHighLight() throws IOException, InterruptedException {
//        //given
//        Record record = new Record();
//        record.setId("1");
//        record.setRecordPath("/record/test.webm");
//        recordRepository.save(record);
//
//        HighLightMessageDto highLightMessageDto = new HighLightMessageDto();
//        highLightMessageDto.setGameSeq(1L);
//        highLightMessageDto.setHighLightPositionList(new ArrayList<>());
//        highLightMessageDto.getHighLightPositionList().add(new HighLightPosition(10,15));
//        highLightMessageDto.getHighLightPositionList().add(new HighLightPosition(20,25));
//        highLightMessageDto.getHighLightPositionList().add(new HighLightPosition(600,605));
//
//        //when
//        videoService.makeHighLight(highLightMessageDto);
//        videoService.sendHighLight(1L);
//
//        //then

    }

    @Test
    void sendHighLight(){

    }


}