package com.delbot.danam.domain.board.exception;

public class NotFoundBoardException extends RuntimeException{
  //
  private static final long serialVersionUID = -3111276572L;

  public NotFoundBoardException(String message){
    super(message);
  }  
}
