package com.delbot.danam.domain.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.delbot.danam.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
  //
  List<Board> findByBoardType(Long boardType);
  Page<Board> findByBoardType(Long boardType, Pageable pageable);
  Optional<Board> findByBoardTypeAndBoardSequence(Long boardType, Long boardSequence);
  @Modifying
  @Query(value = "UPDATE Board b SET b.boardHits=b.boardHits+1 WHERE b.id=:id")
  void updateHits(@Param("id") Long id);
}
