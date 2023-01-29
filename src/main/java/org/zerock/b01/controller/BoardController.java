package org.zerock.b01.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/list")
	public void list(PageRequestDTO pageRequestDTO,Model model) {
		//PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
		
		PageResponseDTO<BoardListReplyCountDTO> responseDTO
				= boardService.listWithReplyCount(pageRequestDTO);
		log.info(responseDTO);
		model.addAttribute("responseDTO",responseDTO);
	}
	
	@GetMapping("/register")
	public void registerGET() {
		
	}
	
	@PostMapping("/register")
	public String registerPost(@Valid BoardDTO boardDTO
			,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		log.info("board POST register.........");
		
		if(bindingResult.hasErrors()) {
			log.info("has errors..........");
			redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
			return "redirect:/board/register";
		}
		log.info(boardDTO);
		/*BoardDTO(bno=null, title=ㅂㅈㄷㅂㅈㄷㅅ, content=ㅂㄷㅅㅂㄷㅅ, writer=ㅂㄷㅅㅄㄷ, regDate=null, modDate=null
		 * , fileNames=[fcb90314-4c27-4229-adc2-1113a41f163e_직업관.JPG, 992d204c-0b5f-4ddd-8079-f2d7e93acb05_캡처.JPG])*/
		Long bno = boardService.register(boardDTO);
		//아.. bno를 리턴하는 이유가, 등록 후 그 bno값을 "잠깐" view에 남기기 위해서
		redirectAttributes.addFlashAttribute("result",bno);
		return "redirect:/board/list";
	}
	
	@GetMapping({"/read","/modify"})
	public void read(Long bno,PageRequestDTO pageRequestDTO, Model model) {
		BoardDTO boardDTO = boardService.readOne(bno);
		log.info(boardDTO);
		model.addAttribute("dto",boardDTO);
	}
	
	//수정 후에는, 기존의 검색 조건에 안 맞을 수 있으므로,
	//수정 후에는 검색 조건 없이 단순히 조회(read)화면으로 이동하게 구현
	@PostMapping("/modify")
	public String modify(PageRequestDTO pageRequestDTO,
						@Valid BoardDTO boardDTO,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		log.info("board modify post....." + boardDTO);
		
		if(bindingResult.hasErrors()) {
			log.info("has errors..........");
			String link = pageRequestDTO.getLink();
			redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
			redirectAttributes.addAttribute("bno",boardDTO.getBno());
			return "redirect:/board/modify?"+link;
		}
		boardService.modify(boardDTO);
		redirectAttributes.addFlashAttribute("result","modified");
		redirectAttributes.addAttribute("bno",boardDTO.getBno());
		return "redirect:/board/read";
	}
	
	@PostMapping("/remove")
	public String remove(Long bno,RedirectAttributes redirectAttributes) {
		log.info("remove post.." + bno);
		boardService.remove(bno);
		redirectAttributes.addFlashAttribute("result","removed");
		return "redirect:/board/list";
	}
}
