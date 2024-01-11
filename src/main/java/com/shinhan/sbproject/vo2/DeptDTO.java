package com.shinhan.sbproject.vo2;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_dept_young")
public class DeptDTO {
	
	@Id
	Long deptId;
	String deptName;
	
	// oneToMany만 쓰면 list면 중간 테이블이 만들어진다.
	// 따라서 join도 써줘야 한다.
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_id")
	List<EmpDTO> emplist;
}
