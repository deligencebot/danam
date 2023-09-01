package com.delbot.danam.domain.board.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BoardDTO {
  //
  private Long id;
  private Long boardSequence;
  private Long boardType;
  @NotBlank(message = "제목을 입력하세요.")
  private String boardTitle;
  private String boardWriter;
  @NotBlank(message = "내용을 입력하세요.")
  private String boardWriterNick;
  private String boardContents;
  private Long boardHits;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;
  private Long boardIsModified;
  private Long boardIsNotice;
  private Long boardIsCommentable;

  @Builder
  public BoardDTO(Long id, Long boardSequence, Long boardType, String boardTitle, String boardWriter, String boardContents, Long boardHits, Long boardIsModified, Long boardIsNotice, Long boardIsCommentable) {
    //
    this.id = id;
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
}
