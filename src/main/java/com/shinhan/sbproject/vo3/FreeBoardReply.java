package com.shinhan.sbproject.vo3;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tbl_free_replies_young")
public class FreeBoardReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rno;
	private String reply;
	private String replyer;
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@ManyToOne // FK : board_bno
	FreeBoard board2;

}
