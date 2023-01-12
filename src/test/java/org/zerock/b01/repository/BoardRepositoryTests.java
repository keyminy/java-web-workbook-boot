package org.zerock.b01.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void testInsert() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Board board = Board.builder()
					.title("title..." + i)
					.content("content..."+i)
					.writer("user"+(i%10))
					.build();
			Board result = boardRepository.save(board);
			log.info("bno : " + result.getBno());
		});
	}
	
	@Test
	public void testSelect() {
		Long bno = 100L;
		Optional<Board> result = boardRepository.findById(bno);
		Board board = result.orElseThrow();//Optional에 값이 없으면, Exception을 throw
		log.info(board);
	}
	
	@Test
	public void testUpdate() {
		Long bno = 100L;
		Optional<Board> result = boardRepository.findById(bno);
		Board board = result.orElseThrow();//Optional에 값이 없으면, Exception을 throw
		board.change("update..title 100","update.. content 100");
		boardRepository.save(board);
	}
	
	@Test
	public void testDelete() {
		Long bno = 1L;
		boardRepository.deleteById(bno);
	}
}
