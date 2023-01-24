package org.zerock.b01.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{

	@Id
	private String uuid;
	
	private String fileName;
	
	private int ord;
	
	@ManyToOne
	private Board board;

	public void changeBoard(Board board) {
		this.board = board;
	}

	@Override
	public int compareTo(BoardImage other) {
		return this.ord-other.ord;
	}
}
