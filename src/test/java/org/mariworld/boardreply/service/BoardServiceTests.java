package org.mariworld.boardreply.service;

import org.junit.jupiter.api.Test;
import org.mariworld.boardreply.dto.BoardDTO;
import org.mariworld.boardreply.dto.PageRequestDTO;
import org.mariworld.boardreply.dto.PageResultDTO;
import org.mariworld.boardreply.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void registerTest(){
        BoardDTO dto = BoardDTO.builder()
                .title("service test...")
                .content("service test content ...")
                .writerEmail("user1@aaa.com")
                .build();
        boardService.register(dto);
    }

    @Test
    public void getListTest(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);
        result.getDtoList().forEach(System.out::println);
    }

    @Test
    public void getTest(){
        BoardDTO dto = boardService.get(100L);
        System.out.println(dto);
    }

    @Test
    public void removeTest(){
        boardService.removeWithReplies(1L);
    }

    @Test
    public void modifyTest(){
        BoardDTO dto = BoardDTO.builder()
                .bno(2L)
                .title("modify test...")
                .content("modify content...")
                .build();
        boardService.modify(dto);
    }
}
