package com.delbot.danam.domain.board.service.logic;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceLogic implements BoardService {
  //
  private final BoardRepository boardRepository;
  private final ModelMapper mapper;

  @Override
  public BoardDTO findById(Long id) {
    //
    Optional<Board> optionalBoard = boardRepository.findById(id);
    if(optionalBoard.isPresent()) {
      return mapper.map(optionalBoard.get(), BoardDTO.class);
    } else {
      return null;
    }
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
  public void write(BoardDTO boardDTO) { boardRepository.save(mapper.map(boardDTO, Board.class)); }
    
  @Override
  public void update(BoardDTO boardDTO) { boardRepository.save(mapper.map(boardDTO, Board.class)); }

  @Override
  public void delete(Long id) { boardRepository.deleteById(id); }

  @Override
  public Page<BoardDTO> paging(Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    int pageLimit = 10;
    Page<Board> boardPages = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.Direction.DESC, "boardSequence"));

    Page<BoardDTO> boardDTOs = boardPages.map(board -> new BoardDTO(
      board.getId(),
      board.getBoardSequence(), 
      board.getBoardType(), 
      board.getBoardTitle(), 
      board.getBoardWriter(), 
      board.getBoardContents(), 
      board.getBoardHits(), 
      board.getCreatedTime(), 
      board.getUpdatedTime(), 
      board.getBoardIsModified(),
      board.getBoardIsNotice(), 
      board.getBoardIsCommentable()));

    return boardDTOs;
  }

  @Override
  public Long getLastSequence(Long type) {
    //
    Optional<Board> optionalBoard = boardRepository.findByBoardType(type).stream().reduce((first, second) -> second);

    if (optionalBoard.isPresent()) {
      return optionalBoard.get().getBoardSequence() + 1L ;
    } else {
      return 1L;
    }
  }

  @Override
  @Transactional
  public void updateHits(Long id) {
    boardRepository.updateHits(id);
  }



}
