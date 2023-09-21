package com.delbot.danam.domain.board.exception;

public class NotFoundBoardFileException extends RuntimeException{
  //
  private static final long serialVersionUID = -7456170978L;

  public NotFoundBoardFileException(String message){
    super(message);
  }
}
