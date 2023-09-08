package com.delbot.danam.domain.board.repository;

import java.util.List;
import java.util.Optional;

import com.delbot.danam.domain.board.entity.BoardComment;

public interface BoardCommentCustomRepository {
  //
  List<BoardComment> findByBoardId(Long id);
  Optional<BoardComment> findBoardCommentByIdWithParent(Long id);
}
