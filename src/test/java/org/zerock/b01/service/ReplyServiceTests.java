package org.zerock.b01.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.ReplyDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
	
	@Autowired
	private ReplyService replyService;
	
	@Test
	public void testRegister() {
		//given
		ReplyDTO replyDTO = ReplyDTO.builder()
							.replyText("ReplyDTO Text")
							.replyer("replyer22")
							.bno(100L)
							.build();
		log.info(replyService.register(replyDTO));;
	}
}
