package com.delbot.danam.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.delbot.danam.domain.member.vo.Address;
import com.delbot.danam.domain.member.vo.Gender;
import com.delbot.danam.domain.member.vo.MemberType;
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
  private Long memberSeq;
  private String memberId;
  private String memberPassword;
  private String memberName;
  private String memberNick;
  private MemberType memberType;
  private Gender memberGender;
  private LocalDate memberBirthDay;
  private PhoneNumber memberPhone;
  private Address memberAddress;
  private String memberEmail;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;

  public static MemberDTO joinFormToDTO(MemberJoinForm form) {
    //
    MemberDTO memberDTO = new MemberDTO();

    memberDTO.setMemberId(form.getMemberId());
    memberDTO.setMemberPassword(form.getMemberPassword());
    memberDTO.setMemberName(form.getMemberName());
    memberDTO.setMemberNick(form.getMemberNick());
    memberDTO.setMemberType(MemberType.Member);
    if(form.getMemberGender().equals("Male")) { 
      memberDTO.setMemberGender(Gender.Male);} 
    else {
      memberDTO.setMemberGender(Gender.Female); 
    }
    memberDTO.setMemberBirthDay(form.getMemberBirthDay());
    memberDTO.setMemberPhone(PhoneNumber.of(form.getMemberPhoneArea(), form.getMemberPhoneFront(), form.getMemberPhoneBack()));
    memberDTO.setMemberAddress(Address.of(form.getMemberZipCode(), form.getMemberAddress()));
    memberDTO.setMemberEmail(form.getMemberEmailLocal() + "@" + form.getMemberEmailDomain());

    return memberDTO;
  }
}
