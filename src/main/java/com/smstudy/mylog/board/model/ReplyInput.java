package com.smstudy.mylog.board.model;

import com.smstudy.mylog.member.entity.Member;

import lombok.Data;

@Data
public class ReplyInput {

	private long id;
	private long boardId;
	private String content;
	private Member member;
}
