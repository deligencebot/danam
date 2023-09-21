package com.delbot.danam.domain.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.delbot.danam.domain.board.dto.BoardDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_board")
public class Board extends com.delbot.danam.domain.Entity{
  //
  @Column(length = 20, nullable = true)
  private Long boardSequence;

  @Column(length = 10, nullable = true)
  private Long boardType;

  @Column(length = 50, nullable = false)
  private String boardTitle;

  @Column(length = 20, nullable = false)
  private String boardWriter;

  @Column(length = 5000, nullable = false)
  private String boardContents;

  @Column(length = 20, nullable = false)
  private Long boardHits;

  @OneToMany(mappedBy = "board", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<BoardImage> boardImageList = new ArrayList<>();

  @OneToMany(mappedBy = "board", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<BoardFile> boardFileList = new ArrayList<>();

  @OneToMany(mappedBy = "board", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<BoardComment> boardCommentList = new ArrayList<>();

  @Column(length = 1, nullable = false)
  private Long boardIsModified;

  @Column(length = 1, nullable = false)
  private Long boardIsNotice;

  @Column(length = 1, nullable = false)
  private Long boardIsCommentable;

  @Builder
  public Board(Long boardSequence, Long boardType, String boardTitle, String boardWriter, String boardContents, Long boardHits, Long boardIsModified, Long boardIsNotice, Long boardIsCommentable) {
    //
    this.boardSequence = boardSequence;
    this.boardType = boardType;
    this.boardTitle = boardTitle;
    this.boardWriter = boardWriter;
    this.boardContents = boardContents;
    this.boardHits = boardHits;
    this.boardIsModified = boardIsModified;
    this.boardIsNotice = boardIsNotice;
    this.boardIsCommentable = boardIsCommentable;
  }

  public void updateBoard(BoardDTO boardDTO) {
    this.boardTitle = boardDTO.getBoardTitle();
    this.boardContents = boardDTO.getBoardContents();
    this.boardIsModified = 1L;
  }
}
