package com.delbot.danam.domain.member.exception;

public class IdDuplicationException extends RuntimeException{
  //
  private static final long serialVersionUID = 8121173519L;

  public IdDuplicationException(String message) {
    super(message);
  }
}
