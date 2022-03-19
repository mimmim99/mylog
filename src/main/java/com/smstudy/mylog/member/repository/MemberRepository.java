package com.smstudy.mylog.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smstudy.mylog.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findById(long id);
	
	Optional<Member> findByUsername(String username);

	
}
