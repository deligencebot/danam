package com.delbot.danam.domain.board.service;

import java.util.List;

import com.delbot.danam.domain.board.dto.BoardCommentRequestDTO;
import com.delbot.danam.domain.board.dto.BoardCommentResponseDTO;

public interface BoardCommentService {
  //
  public void saveComment(BoardCommentRequestDTO boardCommentRequestDTO, Long boardType, Long boardSequence);
  public List<BoardCommentResponseDTO> viewComments(Long boardId);
  public void delete(Long id);
}
