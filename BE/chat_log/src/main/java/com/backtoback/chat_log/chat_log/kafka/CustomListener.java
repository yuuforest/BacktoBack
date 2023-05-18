package com.backtoback.chat_log.chat_log.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchConsumerAwareMessageListener;
import org.springframework.stereotype.Component;

import com.backtoback.chat_log.chat_log.dto.common.HighLightPosition;
import com.backtoback.chat_log.chat_log.dto.common.ChatMessageDto;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component(value = "customListener")
@Slf4j
public class CustomListener
	implements BatchConsumerAwareMessageListener<String, ChatMessageDto> {

	private final AtomicInteger prevCount = new AtomicInteger(0);
	private final AtomicInteger currentCount = new AtomicInteger(0);

	@Getter
	private final List<HighLightPosition> highLightPositionList = new ArrayList<>();
	private Long mediaStartTime;

	public CustomListener() {
		super();
	}

	public CustomListener(Long mediaStartTime) {
		this.mediaStartTime = mediaStartTime;
	}

	@Override
	public void onMessage(List<ConsumerRecord<String, ChatMessageDto>> data) {
		BatchConsumerAwareMessageListener.super.onMessage(data);

		// int listSize = data.size();
		//
		// log.info("list size(): {}", listSize);
		// count.addAndGet(listSize);
	}

	@Override
	public void onMessage(List<ConsumerRecord<String, ChatMessageDto>> data, Consumer<?, ?> consumer) {
		long highLightStartTime = calcTimeline(8000);

		int listSize = data.size();
		log.info("list size(): {}", listSize);

		int prevCount = this.prevCount.get();
		this.currentCount.set(listSize);
		log.debug("AtomicInteger prevCount 값 : {}", prevCount);
		log.debug("AtomicInteger currentCount 값 : {}", currentCount);

		int currentCount = this.currentCount.get();

		this.addHighLightPositionBycompareCount(prevCount, currentCount, highLightStartTime);
		this.prevCount.set(currentCount);
	}

	// public int getCurrentCount() {
	// 	return this.currentCount.get();
	// }

	/*
	영상 시작시점부터 현재 시각의 차이를 second로 구한다.
	 */
	private double calcOffset(long baseTime, long targetTime) {
		return (targetTime - baseTime) / 1000.0;
	}

	/*
	채팅과 영상의 시간차 보정
	 */
	private long calcTimeline(int intervalMilliSecond) {
		///////////////////////////시간 조정 필요!!!!!!!//////////////////////////
		return System.currentTimeMillis() - intervalMilliSecond;
	}

	private void addHighLightPositionBycompareCount(int prevCount, int currentCount, long highLightStartTime) {
		log.debug("################addHighLightPositionBycompareCount() 진입 ##############");

		////////////////////////////비율 조정 필요!!!!!!!//////////////////////////////
		if (prevCount != 0 && currentCount > 10 && prevCount * 2 <= currentCount) {
			log.debug("################addHighLightPositionBycompareCount() - add 가능!!! ##############");

			this.highLightPositionList.add(
				HighLightPosition.builder()
					.start(calcOffset(this.mediaStartTime, highLightStartTime))
					.build()
			);

			log.info("highListList: {}", Arrays.toString(this.highLightPositionList.toArray()));
		}
	}

}
