package com.delbot.danam.domain.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.delbot.danam.domain.board.dto.BoardDTO;

public interface BoardService {
  //
  public BoardDTO findByTypeAndSequence(Long type, Long seq);
  public Long write(BoardDTO boardDTO);
  public void update(Long type, Long seq, BoardDTO boardDTO);
  public void delete(Long type, Long seq);
  public Page<BoardDTO> getPage(Long type, Pageable pageable);
  public Page<BoardDTO> searchTitle(Long type, String keyword, Pageable pageable);
  public Page<BoardDTO> searchTitleAndContents(Long type, String keyword, Pageable pageable);
  public Page<BoardDTO> searchWriter(Long type, String keyword, Pageable pageable);
  public Long getLastSequence(Long type);
  public void updateHits(Long type, Long seq);
  public String checkMember(Long type, Long seq, String username);
}
