package com.smstudy.mylog.board.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.smstudy.mylog.board.entity.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

	private long id;
	private String content;
	private String username;
	//private long boardId;
	private LocalDateTime regDt;
	
	public static ReplyDto of(Reply reply) {
		return ReplyDto.builder().id(reply.getId())
								  .content(reply.getContent())
								  .username(reply.getMember().getUsername())
								  .regDt(reply.getRegDt())
								  .build();
	}
	
	public static List<ReplyDto> of(List<Reply> replies) {
		List<ReplyDto> list = new ArrayList<>();
		for(Reply r : replies) {
			list.add(ReplyDto.of(r));
		}
		
		return list;
	}
}
