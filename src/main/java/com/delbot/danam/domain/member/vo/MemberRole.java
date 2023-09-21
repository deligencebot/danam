package com.delbot.danam.domain.member.vo;

import lombok.Getter;

@Getter
public enum MemberRole {
  Member("ROLE_MEMBER"),
  Admin("ROLE_ADMIN");

  MemberRole(String value) {
    this.value = value;
  }

  private String value;
}
