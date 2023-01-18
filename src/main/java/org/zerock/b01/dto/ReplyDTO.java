package org.zerock.b01.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	
	private Long rno;
	
	//특정 게시물의 번호
	@NotNull
	private Long bno;
	
	@NotEmpty
	private String replyText;
	
	@NotEmpty
	private String replyer;
	
	private LocalDateTime regDate,modDate;
}