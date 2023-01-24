package org.zerock.b01.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
@ToString(exclude = "imageSet")
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length = 500, nullable = false) //칼럼의 길이와, null 허용 X
	private String title;
	
	@Column(length = 2000, nullable = false) //칼럼의 길이와, null 허용 X
	private String content;
	
	@Column(length = 50, nullable = false) //칼럼의 길이와, null 허용 X
	private String writer;
	
	//연관관계의 주인은 BoardImage의 board
	@OneToMany(mappedBy = "board"
			,cascade = {CascadeType.ALL}
			,fetch = FetchType.LAZY) 
	@Builder.Default
	private Set<BoardImage> imageSet = new HashSet<>();
	
	public void addImage(String uuid,String fileName) {
		BoardImage boardImage = BoardImage.builder()
				.uuid(uuid)
				.fileName(fileName)
				.board(this)
				.ord(imageSet.size())
				.build();
		imageSet.add(boardImage);
	}
	
	public void clearImages() {
		imageSet.forEach(boardImage -> boardImage.changeBoard(null));
		this.imageSet.clear();
	}
	
	public void change(String title,String content) {
		this.title = title;
		this.content = content;
	}
}
