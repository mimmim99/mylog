package com.smstudy.mylog.board.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.smstudy.mylog.board.entity.Board;
import com.smstudy.mylog.board.entity.BoardAccessCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

	private long id;
	private String title;
	private String keywords;
	private String content;
	private BoardAccessCode accessScope;
	private long count;
	private String username;
	private String nickname;
	private boolean postYn;
	private List<ReplyDto> replies;
	private LocalDateTime regDt;
	
	public String getRegDtText() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return regDt != null ? regDt.format(format) : "";
	}
	
	public static BoardDto of(Board board) {
		return BoardDto.builder().id(board.getId())
								 .title(board.getTitle())
								 .keywords(board.getKeywords())
								 .content(board.getContent())
								 .accessScope(board.getAccessScope())
								 .count(board.getCount())
								 .username(board.getMember().getUsername())
								 .nickname(board.getMember().getNickname())
								 .postYn(board.isPostYn())
								 .replies(ReplyDto.of(board.getReplies()))
								 .regDt(board.getRegDt())
								 .build();
	}
	
	public static List<BoardDto> of(List<Board> boards) {
		List<BoardDto> list = new ArrayList<>();
		for(Board b : boards) {
			list.add(BoardDto.of(b));
		}
		
		return list;
	}
}
