package com.delbot.danam.domain.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateForm {

  @Pattern(regexp = "^[a-zA-Z0-9!#%&@':;\\-\\.<>,~]{8,16}$", message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자(!#%&@':;-.<>,~)만 사용하세요.")
  @NotBlank(message = "비밀번호는 필수항목입니다.")
  private String prePassword;

  @Pattern(regexp = "^[a-zA-Z0-9!#%&@':;\\-\\.<>,~]{8,16}$", message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자(!#%&@':;-.<>,~)만 사용하세요.")
  @NotBlank(message = "비밀번호는 필수항목입니다.")
  private String password;

  @Pattern(regexp = "^[a-zA-Z0-9!#%&@':;\\-\\.<>,~]{8,16}$", message = "비밀번호는 8~16자 영문 대소문자, 숫자, 특수문자(!#%&@':;-.<>,~)만 사용하세요.")
  @NotBlank(message = "비밀번호는 필수항목입니다.")
  private String passwordCheck;

  @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,12}$", message = "형식이 올바르지 않습니다.")
  private String nickname;

  @Pattern(regexp = "^[0-9]{10,12}$", message = "전화번호는 -를 빼고 10~12자 숫자만 입력하세요.")
  private String phoneNumber;

  private String address;

  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
  private String email;
}
