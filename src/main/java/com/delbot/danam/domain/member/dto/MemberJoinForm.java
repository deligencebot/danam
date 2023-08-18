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
  private String memberId;
  private String memberPassword;
  private String memberName;
  private String memberNick;
  private String memberGender;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate memberBirthDay;
  private String memberPhoneArea;
  private String memberPhoneFront;
  private String memberPhoneBack;
  private String memberZipCode;
  private String memberAddress;
  private String memberEmailLocal;
  private String memberEmailDomain;
}
