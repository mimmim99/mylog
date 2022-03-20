package com.smstudy.mylog.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smstudy.mylog.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	private String keywords;
	
	@Lob
	private String content;
	
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'PUBLIC'")
	private BoardAccessCode accessScope;
	
	@ColumnDefault("0")
	private long count;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "memberId")
	private Member member;
	
	private boolean postYn;
	
	//기본 패치 전략은 LAZY전략임
	//CascadeType.REMOVE는 board 삭제 시 reply 같이 삭제(사용안하면 댓글이 남아있어 게시글 삭제 처리가 안된다.)
	//mappedBy -> 연관관계의 주인이 아님을 표시, 컬럼이 매핑되는 테이블 이름 지정
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"board"})	//양방향 참조 관계에서 무한참조를 방지하기 위함.
	@OrderBy("id desc")
	private List<Reply> replies;
	
	private LocalDateTime regDt;
	
	@PrePersist
	public void setRegDt() {
		this.regDt = LocalDateTime.now();
	}
}
