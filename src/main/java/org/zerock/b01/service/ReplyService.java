package org.zerock.b01.service;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;

public interface ReplyService {
	/*CRUD*/
	Long register(ReplyDTO replyDTO);
	
	ReplyDTO read(Long rno);
	
	void modify(ReplyDTO replyDTO);
	
	void remove(Long rno);
	
	//게시물의 댓글 페이징 처리
	PageResponseDTO<ReplyDTO> getListOfBoard(Long bno,PageRequestDTO pageRequestDTO);
}
