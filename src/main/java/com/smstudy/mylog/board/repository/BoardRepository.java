package com.smstudy.mylog.board.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smstudy.mylog.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findByPostYnOrderByIdDesc(boolean postYn, Pageable pageable);
	
	Page<Board> findByMemberIdAndPostYnOrderByIdDesc(long id, boolean postYn, Pageable pageable);
	
}
