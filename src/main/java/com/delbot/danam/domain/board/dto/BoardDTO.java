package com.delbot.danam.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.delbot.danam.domain.board.entity.Board;

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
  @Size(max = 50, message = "제목이 너무 깁니다.")
  private String boardTitle;

  private String boardWriter;

  @NotBlank(message = "내용을 입력하세요.")
  @Size(max = 5000, message = "내용이 너무 깁니다.")
  private String boardContents;

  private Long boardHits;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;

  private Long boardIsModified;
  private Long boardIsNotice;
  private Long boardIsCommentable;

  private List<MultipartFile> boardFile;
  private List<String> originalFileName;
  private List<String> savedFileName;

  private List<MultipartFile> boardImage;
  private List<String> originalImageName;
  private List<String> savedImageName;

  @Builder(builderMethodName = "pageFormBuilder", buildMethodName = "buildPageForm")
  public BoardDTO(Long id, Long boardSequence, Long boardType, String boardTitle, String boardWriter, Long boardHits, LocalDateTime createdTime) {
    //
    this.id = id;
    this.boardSequence = boardSequence;
    this.boardType = boardType;
    this.boardTitle = boardTitle;
    this.boardWriter = boardWriter;
    this.boardHits = boardHits;
    this.createdTime = createdTime;
  }

  public static BoardDTO entityToDTO(Board board) {
    return BoardDTO.builder()
    .id(board.getId())
    .boardSequence(board.getBoardSequence())
    .boardType(board.getBoardType())
    .boardTitle(board.getBoardTitle())
    .boardWriter(board.getBoardWriter())
    .boardContents(board.getBoardContents())
    .boardHits(board.getBoardHits())
    .createdTime(board.getCreatedTime())
    .updatedTime(board.getUpdatedTime())
    .boardIsModified(board.getBoardIsModified())
    .boardIsNotice(board.getBoardIsNotice())
    .boardIsCommentable(board.getBoardIsCommentable())
    .build();
  }
}
