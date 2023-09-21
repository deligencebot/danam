package com.delbot.danam.domain.board.exception;

public class NotFoundBoardImageException extends RuntimeException{
  //
  private static final long serialVersionUID = -3553722142L;
  
  public NotFoundBoardImageException(String message) {
    super(message);
  }
}
