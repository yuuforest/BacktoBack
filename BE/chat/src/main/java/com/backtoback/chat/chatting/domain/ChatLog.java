package com.backtoback.chat.chatting.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name="CHATLOG")
public class ChatLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="chatlog_seq")
	private Long chatLogSeq;

	@Column(name="game_seq", nullable = false)
	private Long gameSeq;

	@Column(name = "member_seq", nullable = false)
	private Long memberSeq;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String message;

	@Column(nullable = false)
	private LocalDateTime time;
}
