// package com.backtoback.chat_log.chat_log.kafka;
//
// import java.util.concurrent.ScheduledFuture;
//
// import org.springframework.scheduling.TaskScheduler;
// import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
// import org.springframework.stereotype.Component;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Component
// @RequiredArgsConstructor
// @Slf4j
// public class MessagePollScheduler {
//
// 	private final TaskScheduler taskScheduler;
// 	private final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
// 	private final PollableMessageSourceRunner pollableMessageSourceRunner;
// 	private ScheduledFuture<?> scheduledFuture;
//
// 	public void startTask() {
// 		log.info("----------------startTask()-----------------");
//
// 		// ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
// 		// threadPoolTaskScheduler.setPoolSize(3);
// 		// threadPoolTaskScheduler.initialize();
// 		// threadPoolTaskScheduler.scheduleWithFixedDelay(pollableMessageSourceRunner, 1);
//
// 		scheduledFuture = taskScheduler.scheduleWithFixedDelay(pollableMessageSourceRunner, 1);
// 	}
//
// 	// public void getResult() throws InterruptedException, ExecutionException {
// 	// 	// 태스크가 실행되고 결과를 반환받기 위해 ScheduledFuture.get() 메서드 호출
// 	// 	if (scheduledFuture != null) {
// 	// 		scheduledFuture.get();
// 	// 	}
// 	// }
//
// 	public void cancelTask() {
// 		log.info("----------------cancelTask()-----------------");
//
// 		// 예약된 태스크 취소
// 		if (scheduledFuture != null) {
// 			scheduledFuture.cancel(false);
// 		}
// 	}
//
// }