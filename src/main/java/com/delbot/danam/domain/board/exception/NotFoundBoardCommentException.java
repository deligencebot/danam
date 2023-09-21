package com.delbot.danam.domain.board.exception;

public class NotFoundBoardCommentException extends RuntimeException{
  //
  private static final long serialVersionUID = -5862372424L;

  public NotFoundBoardCommentException(String message) {
    super(message);
  }
}
