package com.shinhan.sbproject.vo5;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class DeptVO {
	// wrapper class로 바꾼 이유!
	// null 값이 들어올 때 기본형은 null이 들어갈 수 없고 wrapper class는 null이 들어갈 수 있다.
	@Id
	private Integer departmentId;
	private String departmentName;
	@Column(nullable = true)
	private Integer managerId;
	@Column(nullable = true)
	private Integer locationId;
}
