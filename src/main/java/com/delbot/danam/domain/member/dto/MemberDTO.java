package com.delbot.danam.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.delbot.danam.domain.member.vo.Gender;
import com.delbot.danam.domain.member.vo.MemberRole;

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
public class MemberDTO {
  //
  private Long id;
  private String username;
  private String password;
  private String name;
  private String nickname;
  private MemberRole role;
  private Gender gender;
  private LocalDate birthDay;
  private String phoneNumber;
  private String address;
  private String email;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;

  public static MemberDTO formToDto(MemberJoinForm form) {
    //
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setUsername(form.getUsername());
    memberDTO.setPassword(form.getPassword());
    memberDTO.setName(form.getName());
    
    if(form.getNickname().isBlank()) {
      memberDTO.setNickname(form.getName());
    } else {
      memberDTO.setNickname(form.getNickname());
    }

    memberDTO.setRole(MemberRole.Member);

    if(form.getGender().equals("Male")) {
      memberDTO.setGender(Gender.Male);
    } else {
      memberDTO.setGender(Gender.Female);
    }

    memberDTO.setBirthDay(form.getBirthDay());
    memberDTO.setPhoneNumber(form.getPhoneNumber());
    memberDTO.setAddress(form.getAddress());
    memberDTO.setEmail(form.getEmail());

    return memberDTO;
  }
}
