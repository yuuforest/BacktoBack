package com.backtoback.chat_log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backtoback.chat_log.chat_log.dto.response.GameTeamSeqResponseDto;
import com.backtoback.chat_log.chat_log.service.BusinessService;

@SpringBootTest
class ChatLogApplicationTests {

	@Autowired
	BusinessService businessService;

	@Test
	void contextLoads() {
		GameTeamSeqResponseDto responseDto = businessService.getGameTeamSeqAndTopicNumber(1L);
		System.out.println(responseDto);
	}

}
