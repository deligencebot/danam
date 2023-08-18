package com.delbot.danam.domain.member.service;

import java.util.List;

import com.delbot.danam.domain.member.dto.MemberDTO;

public interface MemberService {
  
  void join(MemberDTO memberDTO);
  List<MemberDTO> findAll();
  MemberDTO findMemberBySeq(Long seq);
  MemberDTO findMemberById(String id);
  MemberDTO findMemberByName(String name);
  MemberDTO update(MemberDTO memberDTO);
  void delete(Long seq);
  MemberDTO login(MemberDTO memberDTO);
  String idDuplicationCheck(String id);
}
