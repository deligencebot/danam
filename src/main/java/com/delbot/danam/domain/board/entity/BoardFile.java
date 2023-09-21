package com.delbot.danam.domain.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_board_file")
public class BoardFile extends com.delbot.danam.domain.Entity{
  //
  @Column(nullable = false)
  private String originalName;

  @Column(unique = true, nullable = false)
  private String savedName;

  @Column(unique = true, nullable = false)
  private String savedPath;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @Builder
  public BoardFile(String originalName, String savedName, String savedPath, Board board) {
    this.originalName = originalName;
    this.savedName = savedName;
    this.savedPath = savedPath;
    this.board = board;
  }
}


