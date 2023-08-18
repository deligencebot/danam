package com.delbot.danam.domain.member.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.delbot.danam.domain.BaseEntity;
import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.vo.Address;
import com.delbot.danam.domain.member.vo.Gender;
import com.delbot.danam.domain.member.vo.MemberType;
import com.delbot.danam.domain.member.vo.PhoneNumber;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_member")
public class MemberEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberSeq;
  
  @Column(unique = true, length = 20, nullable = false)
  private String memberId;

  @Column(length = 20, nullable = false)
  private String memberPassword;

  @Column(length = 20, nullable = false)
  private String memberName;

  @Column(unique = true, length = 20, nullable = false)
  private String memberNick;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MemberType memberType;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender memberGender;

  @Column(nullable = false)
  private LocalDate memberBirthDay;

  @Column(nullable = false)
  @Embedded
  private PhoneNumber memberPhone;

  @Column(nullable = false)
  @Embedded
  private Address memberAddress;

  @Column(length = 50, nullable = false)
  private String memberEmail;

  @Builder
  public MemberEntity(Long memberSeq, String memberId, String memberPassword, String memberName, String memberNick, MemberType memberType, Gender memberGender, LocalDate memberBirthDay, PhoneNumber memberPhone, Address memberAddress, String memberEmail) {
    this.memberSeq = memberSeq;
    this.memberId = memberId;
    this.memberPassword = memberPassword;
    this.memberName = memberName;
    this.memberNick = memberNick;
    this.memberType = memberType;
    this.memberGender = memberGender;
    this.memberBirthDay = memberBirthDay;
    this.memberPhone = memberPhone;
    this.memberAddress = memberAddress;
    this.memberEmail = memberEmail;
  }

  public void updateMemberEntity(MemberDTO memberDTO) {
    this.memberPassword = memberDTO.getMemberPassword();
    this.memberName = memberDTO.getMemberName();
    this.memberNick = memberDTO.getMemberNick();
    this.memberType = memberDTO.getMemberType();
    this.memberBirthDay = memberDTO.getMemberBirthDay();
    this.memberPhone = memberDTO.getMemberPhone();
    this.memberAddress = memberDTO.getMemberAddress();
    this.memberEmail = memberDTO.getMemberEmail();
  }
}
