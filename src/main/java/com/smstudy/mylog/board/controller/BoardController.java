package com.smstudy.mylog.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smstudy.mylog.board.dto.BoardDto;
import com.smstudy.mylog.board.service.BoardService;
import com.smstudy.mylog.config.auth.PrincipalDetail;
import com.smstudy.mylog.member.dto.MemberDto;
import com.smstudy.mylog.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/")
	public String index(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<BoardDto> list = boardService.selectPostedBoardList(pageable);

		PageUtil pager = new PageUtil(list.getNumber(), list.getTotalPages());
				
		model.addAttribute("list", list);
		model.addAttribute("page", pager.getPageHtml());
		
		return "index";
	}
	
	@GetMapping(value = {"/board/list", "/board/list/{username}"})
	public String list(Model model, @PathVariable(required = false) String username, @AuthenticationPrincipal PrincipalDetail principal
						, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		if(username == null || username.isBlank()) {
			username = principal.getUsername();
		}
		 
		Page<BoardDto> list = boardService.selectBoardListByUsername(username, true, pageable);
		MemberDto member = MemberDto.builder().username(username)
											  .nickname(list.toList().get(0).getNickname())
											  .build();
		
		PageUtil pager = new PageUtil(list.getNumber(), list.getTotalPages());
		
		model.addAttribute("list", list);
		model.addAttribute("member", member);
		model.addAttribute("page", pager.getPageHtml());
		
		return "/board/list";
	}
	
	@GetMapping("/board/list/temp")
	public String tempList(Model model, @AuthenticationPrincipal PrincipalDetail principal
							, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		Page<BoardDto> list = boardService.selectBoardListByUsername(principal.getUsername(), false, pageable);
		
		PageUtil pager = new PageUtil(list.getNumber(), list.getTotalPages());
		
		model.addAttribute("list", list);
		model.addAttribute("member", principal.getMember());
		model.addAttribute("page", pager.getPageHtml());
		
		return "/board/tempList";
	}
	
	@GetMapping("/board/write")
	public String write() {
		return "/board/writeForm";
	}
	
	/*
	 * 비회원도 전체 공개글 접근 가능하도록 하기위해 /public 으로 지정함.  
	 */
	@GetMapping("/public/board/{id}")
	public String detail(@PathVariable long id, Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		
		boolean isWriter = false;
		
		BoardDto board = boardService.selectBoard(id);
		if(principal != null && board.getUsername().equals(principal.getUsername())) {
			isWriter = true;
		}
		
		if(!isWriter) {
			if(board.isPostYn()) {
				//조회수 증가
				boardService.updateCount(board.getId(), board.getCount()+1);
				board.setCount(board.getCount()+1);
			} else {
				return "/error/forbidden";
			}
		}

		model.addAttribute("board", board);
		
		return "/board/detail";
	}
	
	@GetMapping("/board/{id}/editForm")
	public String edit(@PathVariable long id, Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		
		BoardDto board = boardService.selectBoard(id);
		if(!board.getUsername().equals(principal.getUsername())) {
			return "redirect:/";
		}
		
		model.addAttribute("board", board);
		
		return "/board/editForm";
	}
}
