package com.delbot.danam.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delbot.danam.domain.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
  //
}
