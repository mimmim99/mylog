package com.smstudy.mylog.board.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smstudy.mylog.board.dto.BoardDto;
import com.smstudy.mylog.board.entity.Board;
import com.smstudy.mylog.board.entity.Reply;
import com.smstudy.mylog.board.model.BoardInput;
import com.smstudy.mylog.board.model.ReplyInput;
import com.smstudy.mylog.board.repository.BoardRepository;
import com.smstudy.mylog.board.repository.ReplyRepository;
import com.smstudy.mylog.board.service.BoardService;
import com.smstudy.mylog.common.dto.ServiceResultDto;
import com.smstudy.mylog.member.dto.MemberDto;
import com.smstudy.mylog.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	private final MemberRepository memberRepository;

	@Override
	public ServiceResultDto insertBoard(BoardInput parameter) {

		Board board = Board.builder().title(parameter.getTitle())
									 .keywords(parameter.getKeywords())
									 .content(parameter.getContent())
									 .accessScope(parameter.getAccessScope())
									 .member(parameter.getMember())
									 .postYn(parameter.isPostYn())
									 .build();
		
		boardRepository.save(board);
		
		return new ServiceResultDto();
	}
	
	@Override
	public Page<BoardDto> selectPostedBoardList(Pageable pageable) {
		
		return boardRepository.findByPostYnOrderByRegDtDesc(true, pageable).map(BoardDto::of);
	}
	
	@Override
	public List<BoardDto> selectBoardListByUsername(String username, boolean postYn) {

		MemberDto memberDto = memberRepository.findByUsername(username).map(MemberDto::of).orElseThrow(()->{
			throw new IllegalArgumentException("게시글 리스트 조회 실패 - 존재하지 않은 회원입니다.");
		});
		
		return boardRepository.findByMemberIdAndPostYnOrderByRegDtDesc(memberDto.getId(), postYn).map(BoardDto::of).orElse(null);
	}

	@Override
	public BoardDto selectBoard(long id) {

		return boardRepository.findById(id).map(BoardDto::of).orElseThrow(()->{
			throw new IllegalArgumentException("게시글 조회 실패 - 게시글이 존재하지 않습니다.");
		});
	}
	
	@Override
	public void updateCount(long id, long count) {
		//select 통해 컨텍스트 올리기
		Board board = boardRepository.findById(id).orElseThrow(()->{
			throw new IllegalArgumentException("게시글 조회수 수정 실패 - 게시글 정보를 찾을 수 없습니다.");
		});
		
		board.setCount(count);
	}

	@Override
	public ServiceResultDto updateBoard(BoardInput parameter) {
		//select 통해 컨텍스트 올리기
		Board board = boardRepository.findById(parameter.getId()).orElseThrow(()->{
			throw new IllegalArgumentException("게시글 수정 실패 - 게시글 정보를 찾을 수 없습니다.");
		});
		
		//수정정보 세팅 후 리턴 시 트랜잭션 종료되어 수정사항 반영됨.
		board.setTitle(parameter.getTitle());
		board.setKeywords(parameter.getKeywords());
		board.setContent(parameter.getContent());
		board.setAccessScope(parameter.getAccessScope());
		board.setPostYn(parameter.isPostYn());
		
		return new ServiceResultDto();
	}

	@Override
	public ServiceResultDto deleteBoard(BoardInput parameter) {

		Board board = boardRepository.findById(parameter.getId()).orElseThrow(()->{
			throw new IllegalArgumentException("게시글 삭제 실패 - 게시글 정보를 찾을 수 없습니다.");
		});
		
		boardRepository.delete(board);
		
		return new ServiceResultDto();
	}
	
	@Override
	public ServiceResultDto insertReply(ReplyInput parameter) {

		Board board = boardRepository.findById(parameter.getBoardId()).orElseThrow(()->{
			throw new IllegalArgumentException("댓글 등록 실패 - 존재하지 않는 게시글입니다. BoardId : " + parameter.getBoardId());
		});
		
		Reply reply = Reply.builder().content(parameter.getContent())
									 .member(parameter.getMember())
									 .board(board)
									 .build();
		
		replyRepository.save(reply);
		
		return new ServiceResultDto();
	}

	@Override
	public ServiceResultDto deleteReply(ReplyInput parameter) {

		Reply reply = replyRepository.findById(parameter.getId()).orElseThrow(()->{
			throw new IllegalArgumentException("댓글 삭제 실패 - 존재하지 않는 댓글입니다.");
		});
		
		//컨트롤러에서 넘긴 세션 값과 댓글 등록 유저가 같은지 체크
		if(!reply.getMember().getUsername().equals(parameter.getMember().getUsername())) {
			return new ServiceResultDto(false, "삭제 권한이 없습닏나.");
		}
		
		replyRepository.delete(reply);
		
		return new ServiceResultDto();
	}

}
