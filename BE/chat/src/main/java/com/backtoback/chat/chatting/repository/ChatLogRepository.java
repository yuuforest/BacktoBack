package com.backtoback.chat.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backtoback.chat.chatting.domain.ChatLog;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}
