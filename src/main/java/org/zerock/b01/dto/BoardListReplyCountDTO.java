package org.zerock.b01.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardListReplyCountDTO {
	private Long bno;
	private String title;
	private String writer;
	private LocalDateTime regDate;
	private Long replyCount;
}
