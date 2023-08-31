package com.delbot.danam.domain.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.delbot.danam.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
  //
  List<Board> findByBoardType(Long boardType);
  @Modifying
  @Query(value = "update Board b set b.boardHits=b.boardHits+1 where b.id=:id")
  void updateHits(@Param("id") Long id);
}
