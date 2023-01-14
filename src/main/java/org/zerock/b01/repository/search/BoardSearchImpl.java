package org.zerock.b01.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;

import com.querydsl.jpa.JPQLQuery;

import sources.annotationProcessor.java.main.org.zerock.b01.domain.QBoard;

public class BoardSearchImpl extends QuerydslRepositorySupport
implements BoardSearch {

	//하위 클래스 생성자는 상위 클래스 생성자를 호출해야함
	public BoardSearchImpl() {
		super(Board.class);
	}

	@Override
	public Page<Board> search1(Pageable pageable) {
		QBoard board = QBoard.board; //Q도메인 객체
		JPQLQuery<Board> query = from(board);// select.. from board
		query.where(board.title.contains("1")); //where title like...
		List<Board> list = query.fetch(); //fetch() : JPQL 쿼리 실행
		long count = query.fetchCount(); //count쿼리 실행가능
		return null;
	}

}
