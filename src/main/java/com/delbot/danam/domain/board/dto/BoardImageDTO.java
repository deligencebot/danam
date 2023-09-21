package com.delbot.danam.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardImageDTO {
  //
  private Long id;
  private String originalName;
  private String savedName;
  private String savedPath;
}
