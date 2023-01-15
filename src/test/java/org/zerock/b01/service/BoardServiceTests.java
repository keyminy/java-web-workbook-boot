package org.zerock.b01.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

	@Autowired
	private BoardService boardService;
	
	@Test
	public void testRegister() {
		//boardService 변수가 가르키는 객체의 클래스명 출력
		log.info(boardService.getClass().getName());
		//org.zerock.b01.service.BoardServiceImpl$$EnhancerBySpringCGLIB$$52fc24bb
		//스프링에서 BoardServiceImpl을 감싸서 만든 클래스 정보가 출력됨
		
		BoardDTO boardDTO = BoardDTO.builder()
								.title("Sample Title...")
								.content("Sample Content...")
								.writer("user00")
								.build();
		Long bno = boardService.register(boardDTO);
		log.info("bno : " + bno);
	}
	
	@Test
	public void testModify() {
		//변경에 필요한 데이터만
		BoardDTO boardDTO = BoardDTO.builder()
								.bno(2L)
								.title("Updated...101")
								.content("Updated content 101...")
								.build();
		
		boardService.modify(boardDTO);						
	}
	
	@Test
	public void testList() {
		//given
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.type("tcw")
				.keyword("1")
				.page(1)
				.size(10)
				.build();
		PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
		log.info(responseDTO);
	}
	
}
