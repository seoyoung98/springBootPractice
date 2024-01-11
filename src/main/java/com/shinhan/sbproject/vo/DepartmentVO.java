package com.shinhan.sbproject.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dept_board1_young")
//@SequenceGenerator(name = "DEPT_SEQ_GENERATOR", sequenceName = "DEPT_SEQ", initialValue = 1, allocationSize = 1)
public class DepartmentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.AUTO, generator = "DEPT_SEQ_GENERATOR")
	// oracle : sequence, mysql : auto increment(identity), auto이면 테이블로 만들어짐
	private int departmentId;
	
	@NonNull
	@Column(length = 50) // 자리수 주기
	private String departmentName;
	
	private int managerId;
	
	private int locationId;
	
	@CreationTimestamp 
	private Timestamp regDate;
	
	@UpdateTimestamp 
	private Timestamp updateDate;
}
