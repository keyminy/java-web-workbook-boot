package org.zerock.b01.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;
	
	@ApiOperation(value="Replies of Board",notes = "GET 방식으로 특정 게시물의 댓글 목록 조회")
	@GetMapping("/list/{bno}")
	public PageResponseDTO<ReplyDTO> getList(@PathVariable("bno") Long bno,
												PageRequestDTO pageRequestDTO){
		PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno, pageRequestDTO);
		return responseDTO;
	}
	
	@ApiOperation(value="Read Reply",notes = "GET 방식으로 특정 댓글 조회")
	@GetMapping("/{rno}")
	public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno){
		ReplyDTO replyDTO = replyService.read(rno);
		return replyDTO;
	}
	
	@ApiOperation(value="Replies POST",notes = "POST 방식으로 댓글 등록")
	@PostMapping(value="/",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Long>> register(@Valid @RequestBody ReplyDTO replyDTO
			,BindingResult bindingResult) throws BindException{
		log.info(replyDTO);
		
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		
		Map<String,Long> resultMap = new HashMap<>();
		Long rno = replyService.register(replyDTO);
		resultMap.put("rno", rno);
		return new ResponseEntity<Map<String,Long>>(resultMap,HttpStatus.OK);
	}
	
	@ApiOperation(value="Modify Reply",notes = "PUT 방식으로 댓글 수정")
	@PutMapping(value="/{rno}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,Long> modify(@PathVariable("rno") Long rno
								,@RequestBody ReplyDTO replyDTO){
		replyDTO.setRno(rno); //번호를 일치시킴 중요!!
		replyService.modify(replyDTO);
		Map<String,Long> resultMap = new HashMap<>();
		resultMap.put("rno",rno);
		return resultMap;
	}
	
	@ApiOperation(value="Delete Reply",notes = "DELETE 방식으로 댓글 삭제")
	@DeleteMapping("/{rno}")
	public Map<String,Long> remove(@PathVariable("rno") Long rno){
		replyService.remove(rno);
		Map<String,Long> resultMap = new HashMap<>();
		resultMap.put("rno",rno);
		return resultMap;
	}
}
