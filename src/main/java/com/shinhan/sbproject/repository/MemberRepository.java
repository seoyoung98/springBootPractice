package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.MemberDTO;

public interface MemberRepository extends CrudRepository<MemberDTO, String>, QuerydslPredicateExecutor<MemberDTO> {

	// JPQL 이용 
	// 해당 멤버의 profile 건수
	@Query("select count(p) from MemberDTO m inner join ProfileDTO p on (m = p.member) where m.mid = ?1")
	int getProfileCountByMember(String mid);
	
	
	// 멤버 정보를 이용해서 모든 멤버의 profile의 개수를 알아내자
	@Query("select m.mid, count(p) from MemberDTO m left outer join ProfileDTO p on(m = p.member) group by m.mid")
	List<Object[]> getProfileCountByMember();
	
	// 멤버 정보를 이용해서 모든 멤버의 profile의 개수를 알아내자
	// native query 사용 
	@Query(value = "select m.mid, count(p.member_mid) from tbl_members_young5 m left outer join tbl_profile_young5 p on(m.mid = p.member_mid) group by m.mid", nativeQuery = true)
	List<Object[]> getProfileCountByMember2();
}
