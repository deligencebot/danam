package com.delbot.danam.domain.board.service;

import java.util.List;

import com.delbot.danam.domain.board.dto.BoardCommentRequestDTO;
import com.delbot.danam.domain.board.dto.BoardCommentResponseDTO;

public interface BoardCommentService {
  //
  public void saveComment(Long boardId, BoardCommentRequestDTO boardCommentRequestDTO);
  public List<BoardCommentResponseDTO> viewComments(Long boardId);
  public Long updateComment(Long id, BoardCommentRequestDTO boardCommentRequestDTO);
  public Long deleteComment(Long id);
  public String checkMember(String memberName, Long id);
}
