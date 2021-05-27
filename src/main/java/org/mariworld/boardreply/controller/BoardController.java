package org.mariworld.boardreply.controller;

import lombok.RequiredArgsConstructor;
import org.mariworld.boardreply.dto.BoardDTO;
import org.mariworld.boardreply.dto.PageRequestDTO;
import org.mariworld.boardreply.dto.PageResultDTO;
import org.mariworld.boardreply.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        model.addAttribute("result",result);
    }

    @GetMapping("/register")
    public void register(){ }

    @PostMapping("/register")
    public String register(BoardDTO dto, RedirectAttributes attr){
        Long bno = boardService.register(dto);
        attr.addFlashAttribute("msg",bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO
            , Long bno, Model model){
        model.addAttribute("dto",boardService.get(bno));
    }

   @PostMapping("/modify")
    public String modify(BoardDTO dto, RedirectAttributes rttr
            ,@ModelAttribute("requestDto") PageRequestDTO pageRequestDTO
            ,Long bno){
        boardService.modify(dto);
        rttr.addAttribute("bno",dto.getBno());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/board/read";
   }

   @PostMapping("/remove")
    public String remove(RedirectAttributes rttr, Long bno){
        boardService.removeWithReplies(bno);
        rttr.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
   }
}
