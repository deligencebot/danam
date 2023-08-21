package com.delbot.danam.domain.member.exception;

public class NoSuchIdException extends RuntimeException{
  //
  private static final long serialVersionUID = -982666119L;

  public NoSuchIdException(String message) {
    super(message);
  }
}
