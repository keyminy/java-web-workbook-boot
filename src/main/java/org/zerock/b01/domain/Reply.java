package org.zerock.b01.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Reply",indexes = {
	@Index(name = "idx_reply_board_bno",columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude="board")
@ToString
public class Reply extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
	
	private String replyText;
	
	private String replyer;
	
	public void changeText(String text) {
		this.replyText = text;
	}
}
