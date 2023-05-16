package com.backtoback.chat_log.chat_log.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "MEMBERS")
public class Member implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Identity로 하면 디비엔진에 따라 오토 인크리먼트가 안먹는다.
	@Column(name = "member_seq")
	private Long memberSeq;

	@Column(name = "member_id", nullable = false, length = 50, unique = true)
	private String memberId;

	@Column(name = "member_password", nullable = false, length = 150)
	private String memberPassword;

	@Column(name = "nickname", nullable = false, length = 50, unique = true)
	private String nickname;

	@Column(name = "point", nullable = false)
	@ColumnDefault(value = "0")
	private Integer point;

	@Column(name = "betting_total", nullable = false)
	@ColumnDefault(value = "0")
	private Integer bettingTotal;

	@Column(name = "betting_win", nullable = false)
	@ColumnDefault(value = "0")
	private Integer bettingWin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "my_team_seq")
	private Team team;

	//////////////////////////////////////

	//==== 비즈니스 로직 ====//

}
