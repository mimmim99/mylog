package com.smstudy.mylog.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.smstudy.mylog.board.dto.BoardDto;
import com.smstudy.mylog.board.model.BoardInput;
import com.smstudy.mylog.board.model.ReplyInput;
import com.smstudy.mylog.common.dto.ServiceResultDto;

public interface BoardService {
	
	/**
	 * 게시글 추가
	 */
	@Transactional
	ServiceResultDto insertBoard(BoardInput parameter);

	/**
	 * 인덱스 페이지용 게시글 리스트(임시작성글 제외) 
	 */
	@Transactional
	Page<BoardDto> selectPostedBoardList(Pageable pageable);
	
	/**
	 * 회원 게시글 리스트 추출
	 * @param postYn (true = 게시글, false = 임시글)
	 */
	@Transactional
	Page<BoardDto> selectBoardListByUsername(String username, boolean postYn, Pageable pageable);

	/**
	 * 게시글 상세정보 추출 
	 */
	@Transactional
	BoardDto selectBoard(long id);
	
	/**
	 * 게시글 방문자수 카운트 변경
	 */
	@Transactional
	void updateCount(long id, long count);

	/**
	 * 게시글 수정 
	 */
	@Transactional
	ServiceResultDto updateBoard(BoardInput parameter);
	
	/**
	 * 게시글 삭제
	 */
	@Transactional
	ServiceResultDto deleteBoard(BoardInput parameter);

	/**
	 * 게시글 댓글 등록 
	 */
	@Transactional
	ServiceResultDto insertReply(ReplyInput parameter);

	/**
	 * 게시글 댓글 삭제
	 */
	@Transactional
	ServiceResultDto deleteReply(ReplyInput parameter);
	
}
