package com.delbot.danam.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delbot.danam.domain.board.entity.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long>, BoardCommentCustomRepository {
  //
}
