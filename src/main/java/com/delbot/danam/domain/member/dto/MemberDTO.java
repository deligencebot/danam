package com.delbot.danam.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.delbot.danam.domain.member.vo.Address;
import com.delbot.danam.domain.member.vo.Gender;
import com.delbot.danam.domain.member.vo.MemberRole;
import com.delbot.danam.domain.member.vo.PhoneNumber;

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
  private PhoneNumber phoneNumber;
  private Address address;
  private String email;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;

  public static MemberDTO joinFormToDTO(MemberJoinForm form) {
    //
    MemberDTO memberDTO = new MemberDTO();

    memberDTO.setUsername(form.getUsername());
    memberDTO.setPassword(form.getPassword());
    memberDTO.setName(form.getName());
    memberDTO.setNickname(form.getNickname());
    memberDTO.setRole(MemberRole.Member);
    if(form.getGender().equals("Male")) { 
      memberDTO.setGender(Gender.Male);} 
    else {
      memberDTO.setGender(Gender.Female); 
    }
    memberDTO.setBirthDay(form.getBirthDay());
    memberDTO.setPhoneNumber(PhoneNumber.of(form.getPhoneArea(), form.getPhoneFront(), form.getPhoneBack()));
    memberDTO.setAddress(Address.of(form.getZipCode(), form.getAddress()));
    memberDTO.setEmail(form.getEmailLocal() + "@" + form.getEmailDomain());

    return memberDTO;
  }
}
