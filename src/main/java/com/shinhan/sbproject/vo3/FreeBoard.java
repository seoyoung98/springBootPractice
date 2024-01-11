package com.shinhan.sbproject.vo3;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tbl_freeboards_young")
public class FreeBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bno;
	@NonNull
	@Column(nullable = false)
	private String title;
	private String writer;
	private String content;
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	// 자바 객체가 브라우저로 내려갈 때 json data로 변경되어 내려간다. 
	@JsonIgnore // 무한 루프가 되지 않도록 FreeBoard -> FreeBoardReply -> 다시 FreeBoard로 가기는 막아야 한다.
	// 연관관계 설정하기
	// 하나의 board에 댓글이 여러 개이다.
	// mappedby를 하면서 중간 칼럼이 생기지 않고 
	@OneToMany(mappedBy = "board2", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<FreeBoardReply> replies;
	
}
