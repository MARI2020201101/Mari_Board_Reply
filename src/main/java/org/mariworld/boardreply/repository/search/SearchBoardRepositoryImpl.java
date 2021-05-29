package org.mariworld.boardreply.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.QBoard;
import org.mariworld.boardreply.entity.QMember;
import org.mariworld.boardreply.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public void search1() {
        log.info("search1.........");

        //질의하기위한 Q도메인 객체 생성
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //조인하고
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));

        //Board가 아니라 다중 컬럼으로 결과값을 받겠다(=>Tuple 타입활용)
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member.email, reply.count())
                .groupBy(board);

        log.info("--------------------------");
        log.info(jpqlQuery+"");
        log.info("--------------------------");

        List<Tuple> result = tuple.fetch();
        result.forEach(System.out::println);
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member, reply.count());
        //검색조건
        /*select Board.*, Member, count(Reply)
         from Board
            join Member on~
        *   join Reply on~
        * where
            Board.bno>0
            (and
                Board.content like %~% or
                Board.title like %~% or
                Board.writer like %~% )

           group by Board.bno ;
        * */
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        booleanBuilder.and(expression);

        if(type!=null){
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            String[] typeArr = type.split("");
            for(String t : typeArr){
                switch (t){
                    case("t"): conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case("c"): conditionBuilder.or(board.content.contains(keyword));
                        break;
                    case("w"): conditionBuilder.or(member.email.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        //Sort 처리
        Sort sort = pageable.getSort();
        sort.stream().forEach(
                order -> {
                    Order direction
                            = order.getDirection().isAscending()? Order.ASC : Order.DESC;
                    String prop = order.getProperty();
                    PathBuilder orderByExpression = new PathBuilder(Board.class,"board");
                    tuple.orderBy(new OrderSpecifier(direction,orderByExpression.get(prop)));
                }
        );
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        tuple.groupBy(board);

        log.info("--------------------------");
        log.info(jpqlQuery+"");
        log.info("--------------------------");

        List<Tuple> result = tuple.fetch();
        result.forEach(System.out::println);

        Long count = tuple.fetchCount();

        /*
        * Tuple(레코드)를 배열[]로 만들고
        *   -> 그걸 다시 List로 감싼다.
        *
        *  Page는 Collections 확장타입이고.
        * 그 안에 배열 Object[]가 되는것!!
        * */
        return new PageImpl<Object[]>(
                result.stream().map(t->t.toArray()).collect(Collectors.toList())
                ,pageable,count);
    }
}
