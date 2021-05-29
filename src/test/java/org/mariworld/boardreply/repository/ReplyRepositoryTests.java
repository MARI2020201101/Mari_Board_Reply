package org.mariworld.boardreply.repository;

import org.junit.jupiter.api.Test;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,300).forEach(
                i->{
                    long n = (long) (Math.random()*100)+1;
                    Board board = Board.builder().bno(n).build();
                    Reply reply = Reply.builder()
                            .text("reply..."+i)
                            .replyer("guest")
                            .board(board)
                            .build();
                    replyRepository.save(reply);

                }
        );
    }
    @Test @Transactional
    public void readTest1(){

        Reply reply = replyRepository.findById(1L).get();
        System.out.println(reply);
        System.out.println(reply.getBoard());

    }
    @Test
    public void getListTest(){
        Board board = Board.builder().bno(100L).build();
        replyRepository.getReplyByBoardOrderByRno(board);
    }
}
