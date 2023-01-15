package org.zerock.b01.service;

import org.zerock.b01.dto.BoardDTO;

public interface BoardService {
	//등록하기
	Long register(BoardDTO boardDTO);
	//한 건 조회하기
	BoardDTO readOne(Long bno);
	//수정 -> 기존 Entity에서 필요한 부분만 변경하도록
	void modify(BoardDTO boardDTO);
	//삭제
	void remove(Long bno);
}
