package org.mariworld.boardreply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> delete(@PathVariable("rno") Long rno){
        replyService.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO dto){
        log.info("\n modified dto : " + dto);
        replyService.modify(dto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
