// package com.backtoback.chat_log.chat_log.kafka;
//
// import org.springframework.cloud.stream.annotation.Input;
// import org.springframework.cloud.stream.binder.PollableMessageSource;
// import org.springframework.stereotype.Component;
//
// @Component
// public interface ChatMessageProcessor {
//
// 	// String APPLICATIONS_IN = "output";
// 	// String APPROVED_OUT = "approved";
// 	// String DECLINED_OUT = "declined";
// 	//
// 	// @Input(APPLICATIONS_IN)
// 	// SubscribableChannel sourceOfLoanApplications();
// 	//
// 	// @Output(APPROVED_OUT)
// 	// MessageChannel approved();
// 	//
// 	// @Output(DECLINED_OUT)
// 	// MessageChannel declined();
// 	//
// 	String POLLABLE_INPUT = "pollableInput-in-0";
//
// 	//Define our own message source, from where we are going to pull/poll  our messages.
// 	//Pls note that it is an "Input" type, e.g. read only, no writes to this "channel"
// 	@Input(ChatMessageProcessor.POLLABLE_INPUT)
// 	PollableMessageSource pollableInput();
//
// }
