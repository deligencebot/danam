package com.delbot.danam.domain.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.delbot.danam.domain.board.dto.BoardDTO;

public interface BoardService {
  //
  public BoardDTO findById(Long id);
  public List<BoardDTO> findAll();
  public void write(BoardDTO boardDTO);
  public Page<BoardDTO> paging(Pageable pageable);
  public Long getLastSequence(Long type);
  public void updateHits(Long id);
}
