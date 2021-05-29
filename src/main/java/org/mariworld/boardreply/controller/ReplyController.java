package org.mariworld.boardreply.controller;

import lombok.RequiredArgsConstructor;
import org.mariworld.boardreply.dto.ReplyDTO;
import org.mariworld.boardreply.entity.Reply;
import org.mariworld.boardreply.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping(value="/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(
            @PathVariable("bno") Long bno){
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO dto){
        Long rno = replyService.register(dto);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
