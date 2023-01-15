package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;

public interface BoardSearch {
	
	Page<Board> search1(Pageable pageable);

	//검색 조건(types)를 지정하고 keyword로 검색하기
	Page<Board> searchAll(String[] types,String keyword,Pageable pageable);
}
