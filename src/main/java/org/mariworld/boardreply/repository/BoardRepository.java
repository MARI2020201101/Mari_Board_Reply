package org.mariworld.boardreply.repository;

import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    //member를 조인해서 가져온다.
    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //reply를 조인해서 가져온다.
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    //member, board, reply(count) 를 가져온다.
    @Query(value="select b,w,count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "group by b" ,
            countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(PageRequest pageRequest);

    @Query("select b,w,count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "where b.bno =:bno")
    Object getBoardByBno(@Param("bno") Long bno);

    @Query("select b,w,count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "where b.bno =:bno")
    Object[] getBoardByBnoTest(@Param("bno") Long bno);

}
