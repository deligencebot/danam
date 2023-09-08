package com.delbot.danam.domain.board.dto;

import java.util.ArrayList;
import java.util.List;

import com.delbot.danam.domain.board.entity.BoardComment;
import com.delbot.danam.domain.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentResponseDTO {
  //
  private Long id;
  private String commentContents;
  private MemberDTO commentWriter;
  private List<BoardCommentResponseDTO> children = new ArrayList<>();

  @Builder
  public BoardCommentResponseDTO(Long id, String commentContents, MemberDTO commentWriter) {
    this.id = id;
    this.commentContents = commentContents;
    this.commentWriter = commentWriter;
  }
  
  public static BoardCommentResponseDTO mapToDTO(BoardComment boardComment) {
    return boardComment.getIsDeleted() == 1 ?
      BoardCommentResponseDTO.builder()
        .id(boardComment.getId())
        .commentContents("삭제된 댓글입니다.")
        .commentWriter(null)
        .build() :
      BoardCommentResponseDTO.builder()
        .id(boardComment.getId())
        .commentContents(boardComment.getCommentContents())
        .commentWriter(new MemberDTO(boardComment.getMember()))
        .build();
  }
}
