package com.smstudy.mylog.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smstudy.mylog.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<List<Board>> findByMemberIdAndPostYnOrderByRegDtDesc(long id, boolean postYn);

	Page<Board> findByPostYnOrderByRegDtDesc(boolean postYn, Pageable pageable);
	
}
