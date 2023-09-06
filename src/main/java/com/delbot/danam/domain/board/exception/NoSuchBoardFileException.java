package com.delbot.danam.domain.board.exception;

public class NoSuchBoardFileException extends RuntimeException {
  //
  private static final long serialVersionUID = 6125469040L;

  public NoSuchBoardFileException(String message) {
    super(message);
  }
}
