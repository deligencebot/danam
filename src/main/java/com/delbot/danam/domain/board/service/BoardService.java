package com.delbot.danam.domain.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.delbot.danam.domain.board.dto.BoardDTO;

public interface BoardService {
  //
  public BoardDTO findById(Long id);
  public List<BoardDTO> findAll();
  public BoardDTO findByTypeAndSequence(Long type, Long seq);
  public void write(BoardDTO boardDTO);
  public void update(BoardDTO boardDTO);
  public void delete(Long id);
  public Page<BoardDTO> getPage(Long type, Pageable pageable);
  public Long getLastSequence(Long type);
  public BoardDTO updateHits(Long type, Long seq);
}
