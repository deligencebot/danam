package com.delbot.danam.domain.board.exception;

public class NotFoundCommentException extends RuntimeException {
  //
  private static final long serialVersionUID = 818425475L;
  
  public NotFoundCommentException(String message) {
    super(message);
  }
}
