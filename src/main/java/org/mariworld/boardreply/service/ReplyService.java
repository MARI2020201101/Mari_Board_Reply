package org.mariworld.boardreply.service;

import org.mariworld.boardreply.dto.ReplyDTO;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Reply;

import java.util.*;


public interface ReplyService {
    List<ReplyDTO> getList(Long bno);
    void remove(Long rno);
    void modify(ReplyDTO replyDTO);
    Long register(ReplyDTO replyDTO);

    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .bno(reply.getBoard().getBno())
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }

    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder().bno(dto.getBno()).build();
        Reply reply = Reply.builder()
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
        return reply;
    }
}
