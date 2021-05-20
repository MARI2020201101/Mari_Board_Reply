package org.mariworld.boardreply.repository;

import org.junit.jupiter.api.Test;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.mariworld.boardreply.entity.Board.builder;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,100).forEach(i->
            {Member member = Member.builder()
                            .email("user"+i+"@aaa.com")
                            .build();

            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
            }
        );
    }
}
