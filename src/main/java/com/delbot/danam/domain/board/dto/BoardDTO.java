package com.delbot.danam.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
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

  private List<MultipartFile> boardFile;
  private List<String> originalFileName;
  private List<String> savedFilename;

  @Builder(builderMethodName = "pageFormBuilder", buildMethodName = "buildPageForm")
  public BoardDTO(Long id, Long boardSequence, Long boardType, String boardTitle, String boardWriterNick, Long boardHits, LocalDateTime createdTime) {
    //
    this.id = id;
    this.boardSequence = boardSequence;
    this.boardType = boardType;
    this.boardTitle = boardTitle;
    this.boardWriterNick = boardWriterNick;
    this.boardHits = boardHits;
    this.createdTime = createdTime;
  }
}
