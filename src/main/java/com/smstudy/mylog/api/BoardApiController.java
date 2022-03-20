package com.smstudy.mylog.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smstudy.mylog.board.dto.BoardDto;
import com.smstudy.mylog.board.model.BoardInput;
import com.smstudy.mylog.board.model.ReplyInput;
import com.smstudy.mylog.board.service.BoardService;
import com.smstudy.mylog.common.dto.ResponseDto;
import com.smstudy.mylog.common.dto.ServiceResultDto;
import com.smstudy.mylog.config.auth.PrincipalDetail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

	private final BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<?> add(@RequestBody BoardInput parameter, @AuthenticationPrincipal PrincipalDetail principal) {
		parameter.setMember(principal.getMember());
		
		ServiceResultDto result = boardService.insertBoard(parameter);
		if(!result.isResult()) {
			
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board")
	public ResponseDto<?> edit(@RequestBody BoardInput parameter, @AuthenticationPrincipal PrincipalDetail principal) {
		
		BoardDto boardDto = boardService.selectBoard(parameter.getId());
		if(!boardDto.getUsername().equals(principal.getUsername())) {
			return new ResponseDto<String>(HttpStatus.FORBIDDEN.value(), "수정 권한이 없습니다.");
		}
		
		ServiceResultDto result = boardService.updateBoard(parameter);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@DeleteMapping("/api/board")
	public ResponseDto<?> delete(@RequestBody BoardInput parameter, @AuthenticationPrincipal PrincipalDetail principal) {
		
		BoardDto boardDto = boardService.selectBoard(parameter.getId());
		if(!boardDto.getUsername().equals(principal.getUsername())) {
			return new ResponseDto<String>(HttpStatus.FORBIDDEN.value(), "삭제 권한이 없습니다.");
		}
		
		ServiceResultDto result = boardService.deleteBoard(parameter);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/api/board/reply")
	public ResponseDto<?> addReply(@RequestBody ReplyInput parameter, @AuthenticationPrincipal PrincipalDetail principal) {
		
		parameter.setMember(principal.getMember());
		
		ServiceResultDto result = boardService.insertReply(parameter);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/reply")
	public ResponseDto<?> deleteReply(@RequestBody ReplyInput parameter, @AuthenticationPrincipal PrincipalDetail principal) {
		
		parameter.setMember(principal.getMember());
		
		ServiceResultDto result = boardService.deleteReply(parameter);
		
		if(!result.isResult()) {
			return new ResponseDto<String>(HttpStatus.FORBIDDEN.value(), result.getMessage());
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
