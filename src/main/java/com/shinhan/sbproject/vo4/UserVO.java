package com.shinhan.sbproject.vo4;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_user")
public class UserVO {
	@Id
	@Column(name = "user_id") 
	String userid; // userId였다면 db에 자동으로 user_id라고 들어가지만, 아니니까 따로 컬럼명을 지정해준다.
	@Column(name = "user_name")
	String username;

	// 주 테이블에서 참조하기 
	// cascade => 주 테이블 dml 수행시 부 테이블에 영향을 주기 (연관 table에 영향)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "phone_id")
	UserCellPhoneVO phone;

}
