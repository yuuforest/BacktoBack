package com.backtoback.chat.chatting.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaBatchScheduler {

	private final JobLauncher jobLauncher;
	private final Job chatLogJob;

	// @Scheduled(cron="0 0 0 * * *")		//MON-SUN 00:00 AM
	public void runChatLogBatchJob(){
		try{
			//JobParameters for indentifying job
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.toJobParameters();
			log.info("############################# Chat Batch Job Run #############################");
			jobLauncher.run(chatLogJob, jobParameters);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
