package com.delbot.danam.domain.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delbot.danam.domain.board.entity.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long>{
  //
  Optional<BoardImage> findBySavedName(String savedName);
}
