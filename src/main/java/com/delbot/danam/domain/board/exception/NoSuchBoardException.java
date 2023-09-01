package com.delbot.danam.domain.board.exception;

public class NoSuchBoardException extends RuntimeException {
  //
  private static final long serialVersionUID = -14721835L;

  public NoSuchBoardException(String message) {
    super(message);
  }
  
}
