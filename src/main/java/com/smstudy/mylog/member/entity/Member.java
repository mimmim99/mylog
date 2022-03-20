package com.smstudy.mylog.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class Member {
	
	//식별키
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//아이디
	@Column(length=100, nullable = false, unique = true)
	private String username;
	
	@Column(length=255, nullable = false)
	private String password;
	
	@Column(length=50, nullable = false)
	private String email;
	
	@Column(length=50, nullable = false)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("'REQ'")
	private MemberStatus status;
	
	//이메일 인증
	private String authKey;			//인증키
	private boolean authYn;			//인증여부
	private LocalDateTime authDt;	//인증완료일
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'USER'")
	private RoleType role;
	
	private String oauth;
	private LocalDateTime regDt;
	private LocalDateTime withdrawDt;
	
	@PrePersist
	private void setRegDt() {
		this.regDt = LocalDateTime.now();
	}

}
