package org.zerock.b01.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	@Test
	public void testPaging() {
		//1 page order by bno desc
		Pageable pageable = PageRequest.of(0, 10,Sort.by("bno").descending());
		Page<Board> result = boardRepository.findAll(pageable);
		log.info("total count : " + result.getTotalElements());
		log.info("total pages : " + result.getTotalPages());
		log.info("page number : " + result.getNumber());
		log.info("page size : " + result.getSize());
		
		List<Board> todoList = result.getContent();
		//Page<T>에서 정의했던, List<T> 타입 꺼내는구나
		todoList.forEach(board -> log.info(board));
	}
}
