package com.delbot.danam.domain.member.exception;

public class NoSuchMemberException extends RuntimeException{
  //
  private static final long serialVersionUID = 1179686087L;

  public NoSuchMemberException(String message) {
    super(message);
  }
}
