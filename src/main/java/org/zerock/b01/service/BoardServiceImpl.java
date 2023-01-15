package org.zerock.b01.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

	private final ModelMapper modelMapper;
	
	private final BoardRepository boardRepository;
	//등록하기
	@Override
	public Long register(BoardDTO boardDTO) {
		Board board = modelMapper.map(boardDTO, Board.class);
		Long bno = boardRepository.save(board).getBno();
		return bno;
	}

	//한 건 조회 -> DTO를 리턴하게
	@Override
	public BoardDTO readOne(Long bno) {
		Optional<Board> result = boardRepository.findById(bno);
		Board board = result.orElseThrow();
		BoardDTO boardDTO = modelMapper.map(board,BoardDTO.class);
		return boardDTO;
	}
	
	//수정 -> 기존 Entity에서 필요한 부분만 변경하도록(change메서드)
	@Override
	public void modify(BoardDTO boardDTO) {
		Optional<Board> result = boardRepository.findById(boardDTO.getBno());
		Board board = result.orElseThrow();
		board.change(boardDTO.getTitle(), boardDTO.getContent());
		boardRepository.save(board);
		
	}

	@Override
	public void remove(Long bno) {
		boardRepository.deleteById(bno);
	}

}
