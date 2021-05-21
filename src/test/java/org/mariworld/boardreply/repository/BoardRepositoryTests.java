package org.mariworld.boardreply.repository;

import org.junit.jupiter.api.Test;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.transform.sax.SAXSource;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void readTest1(){
        System.out.println(
                "boardRepository.findById(100L) \n"+
                boardRepository.findById(100L).get().toString());

    }

    @Test
    public void joinTest1(){
        Object result  = boardRepository.getBoardWithWriter(1L);
        Object[] board = (Object[])result;
        System.out.println(Arrays.toString(board));

    }

    @Test
    public void joinTest2(){
        List<Object[]> result  = boardRepository.getBoardWithReply(1L);
        for(Object[] o : result){
            System.out.println(Arrays.toString(o));
        }
    }
}
