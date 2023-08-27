package com.delbot.danam.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.vo.Gender;
import com.delbot.danam.domain.member.vo.MemberRole;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_member")
public class Member extends com.delbot.danam.domain.Entity{
  //
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(unique = true, length = 20, nullable = false)
  private String username;

  @Column(length = 100, nullable = false)
  private String password;

  @Column(length = 20, nullable = false)
  private String name;

  @Column(unique = true, length = 20, nullable = false)
  private String nickname;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MemberRole role;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String address;

  @Column(length = 50, nullable = false)
  private String email;

  @Builder
  public Member(Long id, String username, String password, String name, String nickname, MemberRole role, Gender gender, String phoneNumber, String address, String email) {
    //
    this.id = id;
    this.username = username;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.role = role;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.email = email;
  }

  public static Member dtoToEntity(MemberDTO memberDTO, String password) {
    //
    return Member.builder()
    .username(memberDTO.getUsername())
    .password(password)
    .name(memberDTO.getName())
    .nickname(memberDTO.getNickname())
    .role(memberDTO.getRole())
    .gender(memberDTO.getGender())
    .phoneNumber(memberDTO.getPhoneNumber())
    .address(memberDTO.getAddress())
    .email(memberDTO.getEmail())
    .build();
  }
}
