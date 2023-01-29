package org.zerock.b01.service;

import java.util.List;
import java.util.stream.Collectors;

import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListAllDTO;
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
	
	//BoardListAllDTO사용하기, 게시글의 이미지와 댓글의 갯수까지 처리
	PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);
	
	//게시물 등록 시에는, List<String> fileNames; → Set<BoardImage> imageSet으로 타입변환 필요하다
	//DTO -> Entity변환
	//default메소드로 구현해보자
	default Board dtoToEntity(BoardDTO boardDTO) {
		Board board = Board.builder()
				.bno(boardDTO.getBno())
				.title(boardDTO.getTitle())
				.content(boardDTO.getContent())
				.writer(boardDTO.getWriter())
				.build();
		if(boardDTO.getFileNames() != null) {
			boardDTO.getFileNames().forEach(fileName -> {
				String[] arr = fileName.split("_");
				//매개변수 : (String uuid, String fileName)
				board.addImage(arr[0],arr[1]);
			});
		}
		return board;		
	}
	
	//조회하기는 Board엔티티 -> BoardDTO로 볼 수 있게함
	default BoardDTO entityToDTO(Board board) {
		BoardDTO boardDTO = BoardDTO.builder()
				.bno(board.getBno())
				.title(board.getTitle())
				.content(board.getContent())
				.writer(board.getWriter())
				.regDate(board.getRegDate())
				.modDate(board.getModDate())
				.build();
		// Set<BoardImage> imageSet -> List<String> fileNames;으로 타입변환 필요하다
		List<String> fileNames =
				board.getImageSet().stream().sorted().map(boardImage -> {
					return boardImage.getUuid() + "_" + boardImage.getFileName();
				}).collect(Collectors.toList());
		boardDTO.setFileNames(fileNames);
		return boardDTO;
	}
}
