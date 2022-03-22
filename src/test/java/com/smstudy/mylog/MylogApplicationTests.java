package com.smstudy.mylog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smstudy.mylog.board.entity.Board;
import com.smstudy.mylog.board.repository.BoardRepository;
import com.smstudy.mylog.member.entity.Member;
import com.smstudy.mylog.member.repository.MemberRepository;

@SpringBootTest
class MylogApplicationTests {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	BoardRepository boardRepository;

	@Test
	void insertTestData() {
		Member member = memberRepository.findByUsername("bbb").get();
		for(int i = 0; i< 50; i++) {
			int count = (i+1);
			Board board = Board.builder().title("테스트 게시글 + " + count)
										 .keywords("테스트,게시글," + count)
										 .content("테스트 게시글 내용 + " + count)
										 .count(0)
										 .postYn(true)
										 .member(member)
										 .build();
			
			//boardRepository.save(board);
		}
	}

}
