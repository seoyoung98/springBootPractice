package com.shinhan.sbproject.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString // lazy 쓰려면 exclude 해야함 
@EqualsAndHashCode(of = { "pno" })
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_profile_young5")
public class ProfileDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pno;
	private String fname;
	private boolean currentYn;
	// false:0 true:1

	// profile을 통해서 가져온다.
	// eager : 즉시, default
    // lazy
    @ManyToOne(fetch = FetchType.LAZY)
	private MemberDTO member; // member_mid칼럼이 DB생성된다.

	// 직원, 부서
	// 직원이 부서를 참조한다.
	// 직원테이블에 부서의 키를 FK로 생성한다.

	// ProfileDTO가 MemberDTO를 참조한다.
	// tbl_profile테이블에 tbl_members테이블의 키값 mid가 FK로 생성한다.

}