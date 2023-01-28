package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListAllDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;

public interface BoardSearch {
	
	Page<Board> search1(Pageable pageable);

	//검색 조건(types)를 지정하고 keyword로 검색하기
	Page<Board> searchAll(String[] types,String keyword,Pageable pageable);
	
	Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types
													,String keyword
													,Pageable pageable);
	/* N+1문제(p.628~) */
	Page<BoardListAllDTO> searchWithAll(String[] types
										,String keyword
										,Pageable pageable);
	
	
}
