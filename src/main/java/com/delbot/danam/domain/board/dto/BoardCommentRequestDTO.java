package com.delbot.danam.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentRequestDTO {
  //
  private Long id;
  private String commentContents;
  private String memeberName;
  private Long parentId;

  public BoardCommentRequestDTO(String commentContents) {
    this.commentContents = commentContents;
  }

  public void updateMemberName(String memberName) {
    this.memeberName = memberName;
  }
}
