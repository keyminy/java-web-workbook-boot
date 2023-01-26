package org.zerock.b01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long>,BoardSearch{

	@EntityGraph(attributePaths = {"imageSet"})//DataJpa 에서 fetch 조인을 하기 위한 설정
	@Query("SELECT b FROM Board b WHERE b.bno = :bno")
	Optional<Board> findByIdWithImages(@Param("bno") Long bno);
}