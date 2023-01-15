package org.zerock.b01.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;

import com.querydsl.core.BooleanBuilder;
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
		BooleanBuilder booleanBuilder = new BooleanBuilder(); // ( open
 		booleanBuilder.or(board.title.contains("11")); //title like ...
		booleanBuilder.or(board.content.contains("11")); //content like ...
		query.where(booleanBuilder); // ) closing
		query.where(board.bno.gt(0L)); // AND greater than > 0
		//paging
		this.getQuerydsl().applyPagination(pageable,query);
		List<Board> list = query.fetch(); //fetch() : JPQL 쿼리 실행
		long count = query.fetchCount(); //count쿼리 실행가능
		return null;
	}
	
	//검색 조건(types)를 지정하고 keyword로 검색하기
	@Override
	public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
		QBoard board = QBoard.board;
		JPQLQuery<Board> query = from(board);
		if((types != null && types.length > 0) && keyword !=null) {
			//검색 조건과 키워드가 있다면
			BooleanBuilder booleanBuilder = new BooleanBuilder(); // ( open
			for(String type : types) {
				switch(type) {
					case "t":
						booleanBuilder.or(board.title.contains(keyword));
						break;
					case "c":
						booleanBuilder.or(board.content.contains(keyword));
						break;
					case "w":
						booleanBuilder.or(board.writer.contains(keyword));
						break;
				}
			}//end for
			query.where(booleanBuilder); // ) closing
		} //end if
		
		//bno > 0
		query.where(board.bno.gt(0L));
		
		//paging
		this.getQuerydsl().applyPagination(pageable,query);
		List<Board> list = query.fetch(); //fetch() : JPQL 쿼리 실행
		long count = query.fetchCount(); //count쿼리 실행가능
		return new PageImpl<Board>(list, pageable, count);
	}

}
