package org.mariworld.boardreply.service;

import lombok.RequiredArgsConstructor;
import org.mariworld.boardreply.dto.BoardDTO;
import org.mariworld.boardreply.dto.PageRequestDTO;
import org.mariworld.boardreply.dto.PageResultDTO;
import org.mariworld.boardreply.entity.Board;
import org.mariworld.boardreply.entity.Member;
import org.mariworld.boardreply.repository.BoardRepository;
import org.mariworld.boardreply.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        PageRequest pageRequest
                = pageRequestDTO.getPageable(Sort.by("bno").descending());

        Page<Object[]> result
                = boardRepository.getBoardWithReplyCount(pageRequest);

        Function<Object[],BoardDTO> fn
                = (en-> entityToDTO((Board) en[0],(Member) en[1],(Long)en[2]));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        BoardDTO dto =  entityToDTO((Board) arr[0],(Member) arr[1],(Long)arr[2]);
        return dto;
    }

    @Override @Transactional
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override @Transactional
    public void modify(BoardDTO dto) {
        Board board = boardRepository.getOne(dto.getBno());
        String content = dto.getContent();
        String title = dto.getTitle();
        board.changeTitle(title);
        board.changeContent(content);
        boardRepository.save(board);
    }
}
