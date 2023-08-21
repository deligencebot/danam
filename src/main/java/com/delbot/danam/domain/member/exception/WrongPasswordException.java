package com.delbot.danam.domain.member.exception;

public class WrongPasswordException extends RuntimeException{
  //
  private static final long serialVersionUID = 8673754010L;

  public WrongPasswordException(String message) {
    super(message);
  }
}
