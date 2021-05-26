package org.mariworld.boardreply.controller;

import lombok.RequiredArgsConstructor;
import org.mariworld.boardreply.dto.BoardDTO;
import org.mariworld.boardreply.dto.PageRequestDTO;
import org.mariworld.boardreply.dto.PageResultDTO;
import org.mariworld.boardreply.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResultDTO<BoardDTO, Object[]> list = boardService.getList(pageRequestDTO);
        model.addAttribute("list",list);
    }
}
