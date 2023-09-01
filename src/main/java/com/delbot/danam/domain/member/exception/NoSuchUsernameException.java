package com.delbot.danam.domain.member.exception;

public class NoSuchUsernameException extends RuntimeException{
  //
  private static final long serialVersionUID = -4036831505L;

  public NoSuchUsernameException(String message) {
    super(message);
  }
}
