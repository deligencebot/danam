package com.delbot.danam.domain.member.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinForm {
  //
  private String username;
  private String password;
  private String name;
  private String nickname;
  private String Gender;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDay;
  private String phoneArea;
  private String phoneFront;
  private String phoneBack;
  private String zipCode;
  private String address;
  private String emailLocal;
  private String emailDomain;
}
