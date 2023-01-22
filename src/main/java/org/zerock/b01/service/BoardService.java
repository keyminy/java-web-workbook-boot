package org.zerock.b01.service;

import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface BoardService {
	//등록하기
	Long register(BoardDTO boardDTO);
	//한 건 조회하기
	BoardDTO readOne(Long bno);
	//수정 -> 기존 Entity에서 필요한 부분만 변경하도록
	void modify(BoardDTO boardDTO);
	//삭제
	void remove(Long bno);
	
	//목록/검색 기능
	PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
	
	//댓글의 숫자까지 처리
	PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
}
