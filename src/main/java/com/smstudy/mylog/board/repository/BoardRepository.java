package com.smstudy.mylog.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smstudy.mylog.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<List<Board>> findByMemberIdAndPostYnOrderByRegDtDesc(long id, boolean postYn);
	
	Optional<List<Board>> findByPostYnOrderByRegDtDesc(boolean postYn);
	
}
