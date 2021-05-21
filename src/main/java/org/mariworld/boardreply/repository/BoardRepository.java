package org.mariworld.boardreply.repository;

import org.mariworld.boardreply.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //member를 조인해서 가져온다.
    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //reply를 조인해서 가져온다.
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
}
