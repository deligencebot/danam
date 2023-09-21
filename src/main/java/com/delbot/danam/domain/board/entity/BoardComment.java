package com.delbot.danam.domain.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.delbot.danam.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "tb_board_comment")
public class BoardComment extends com.delbot.danam.domain.Entity{
  //
  @Column(length = 500, nullable = false)
  private String commentContents;

  @Column(length = 1, nullable = false)
  private Long isDeleted;

  @ManyToOne(fetch =  FetchType.EAGER)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private BoardComment parent;

  @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<BoardComment> children = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name =  "board_id")
  private Board board;

  @Builder
  public BoardComment(String commentContents, Long isDeleted, Member member ,Board board) {
    this.commentContents = commentContents;
    this.isDeleted = isDeleted;
    this.member = member;
    this.board = board;
  }

  public void updateParent(BoardComment parentComment) {
    this.parent = parentComment;
  }

  public void updateIsDeleted(Long isDeleted) {
    this.isDeleted = isDeleted;
  }

  public void updateComment(String commentContents) {
    this.commentContents = commentContents;
  }
}
