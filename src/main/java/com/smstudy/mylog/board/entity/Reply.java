package com.smstudy.mylog.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.smstudy.mylog.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 200, nullable = false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "boardId")
	Board board;
	
	@ManyToOne
	@JoinColumn(name = "memberId")
	Member member;
	
	private LocalDateTime regDt;
	
	@PrePersist
	public void setRegDt() {
		this.regDt = LocalDateTime.now();
	}
}
