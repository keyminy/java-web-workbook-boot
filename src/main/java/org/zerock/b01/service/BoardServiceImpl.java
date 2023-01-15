package org.zerock.b01.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
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
	
	//목록/검색 기능
	@Override
	public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
		String[] types = pageRequestDTO.getTypes();
		String keyword = pageRequestDTO.getKeyword();
		Pageable pageable = pageRequestDTO.getPageable("bno");
		Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
		
		List<BoardDTO> dtoList = result.getContent().stream()
									.map(board -> modelMapper.map(board, BoardDTO.class))
									.collect(Collectors.toList());
		return PageResponseDTO.<BoardDTO>withAll()
					.pageRequestDTO(pageRequestDTO)
					.dtoList(dtoList)
					.total((int)result.getTotalElements())
					.build();
	}

}
