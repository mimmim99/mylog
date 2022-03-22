package com.smstudy.mylog.board.model;

import com.smstudy.mylog.board.entity.BoardAccessCode;
import com.smstudy.mylog.member.entity.Member;

import lombok.Data;

@Data
public class BoardInput {

	private long id;
	private String title;
	private String keywords;
	private String content;
	private Member member;
	private BoardAccessCode accessScope;
	private boolean postYn;
}
