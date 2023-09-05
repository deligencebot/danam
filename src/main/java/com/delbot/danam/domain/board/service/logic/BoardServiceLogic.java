package com.delbot.danam.domain.board.service.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delbot.danam.domain.board.dto.BoardDTO;
import com.delbot.danam.domain.board.entity.Board;
import com.delbot.danam.domain.board.exception.NoSuchBoardException;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardService;
import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceLogic implements BoardService {
  //
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;
  private final ModelMapper mapper;

  @Override
  public BoardDTO findById(Long id) {
    //
    return mapper.map(boardRepository.findById(id)
    .orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nID : " + id)), BoardDTO.class);
  }

  @Override
  public List<BoardDTO> findAll() {
    //
    List<BoardDTO> boardDTOList = boardRepository.findAll().stream()
    .map(board -> mapper.map(board, BoardDTO.class))
    .collect(Collectors.toList());
    Collections.reverse(boardDTOList);

    return boardDTOList;
  }

  @Override
  public BoardDTO findByTypeAndSequence(Long type, Long seq) {
    //
    return mapper.map(boardRepository.findByBoardTypeAndBoardSequence(type, seq)
    .orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq)), BoardDTO.class);
  }

  @Override
  public Long write(BoardDTO boardDTO) { return boardRepository.save(mapper.map(boardDTO, Board.class)).getId(); }
    
  @Override
  public void update(BoardDTO boardDTO) { boardRepository.save(mapper.map(boardDTO, Board.class)); }

  @Override
  public void delete(Long id) { boardRepository.deleteById(id); }

  @Override
  public Page<BoardDTO> getPage(Long type, Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    int pageLimit = 3;
    Page<Board> boardPages = boardRepository.findByBoardType(type, PageRequest.of(page, pageLimit, Sort.Direction.DESC, "boardSequence"));

    Page<BoardDTO> boardDTOPages = boardPages.map(board -> {
      String writerNickname = memberRepository.findByUsername(board.getBoardWriter())
      .map(Member::getNickname)
      .orElse(board.getBoardWriter());

      return new BoardDTO(
        board.getId(),
        board.getBoardSequence(),
        board.getBoardType(),
        board.getBoardTitle(),
        writerNickname,
        board.getBoardHits(),
        board.getCreatedTime()
        );
    });

    return boardDTOPages;
  }

  @Override
  public Long getLastSequence(Long type) {
    //
    return boardRepository.findByBoardType(type)
    .stream()
    .map(Board::getBoardSequence)
    .max(Comparator.naturalOrder())
    .orElse(0L) + 1L;
  }

  @Override
  @Transactional
  public BoardDTO updateHits(Long type, Long seq) {
    //
    Board board = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
    .orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq));
    boardRepository.updateHits(board.getId());
    return mapper.map(board, BoardDTO.class);
  }
}
