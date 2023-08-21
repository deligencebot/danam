package com.delbot.danam.domain.member.service;

import java.util.List;

import com.delbot.danam.domain.member.dto.MemberDTO;

public interface MemberService {
  
  void join(MemberDTO memberDTO);
  List<MemberDTO> findAll();
  MemberDTO findMemberById(Long id);
  MemberDTO findMemberByUsername(String username);
  MemberDTO findMemberByName(String name);
  MemberDTO update(MemberDTO memberDTO);
  void delete(Long id);
  String usernameDuplicationCheck(String username);
}
