package org.mariworld.boardreply.service;

import org.mariworld.boardreply.dto.BoardDTO;
import org.mariworld.boardreply.dto.PageRequestDTO;
import org.mariworld.boardreply.dto.PageResultDTO;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    BoardDTO get(Long bno);
    void removeWithReplies(Long bno);
    void modify(BoardDTO dto);


    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }
}
