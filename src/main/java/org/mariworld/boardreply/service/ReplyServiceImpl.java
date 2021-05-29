package org.mariworld.boardreply.service;

import lombok.RequiredArgsConstructor;
import org.mariworld.boardreply.dto.ReplyDTO;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Reply;
import org.mariworld.boardreply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    @Override
    public List<ReplyDTO> getList(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> result = replyRepository.getReplyByBoardOrderByRno(board);
        return result.stream().map(r->entityToDTO(r)).collect(Collectors.toList());
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        replyRepository.save(dtoToEntity(replyDTO));
    }

    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return reply.getRno();
    }
}
