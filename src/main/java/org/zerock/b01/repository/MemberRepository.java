package org.zerock.b01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Member;

public interface MemberRepository extends JpaRepository<Member,String>{

	@EntityGraph(attributePaths = "roleSet")
	@Query("SELECT m "
			+ "FROM Member m "
			+ "WHERE m.mid = :mid "
			+ "AND m.social = false")
	Optional<Member> getWithRoles(@Param("mid") String mid);
}
