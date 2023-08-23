package com.delbot.danam.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinForm {
  //
  @Size(min = 6, max = 20)
  @NotEmpty(message = "사용자 ID는 필수항목입니다.")
  private String username;
  
  @NotEmpty(message = "비밀번호는 필수항목입니다.")
  private String password;

  @NotEmpty(message = "비밀번호확인은 필수항목입니다.")
  private String passwordCheck;

  @NotEmpty(message = "이름은 필수항목입니다.")
  private String name;

  private String nickname;

  private String gender;

  private String phoneNumber;

  private String address;

  @Email(message = "이메일 형식이 아닙니다.")
  private String email;
}
