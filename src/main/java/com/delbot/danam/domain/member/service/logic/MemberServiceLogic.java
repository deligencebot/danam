package com.delbot.danam.domain.member.service.logic;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.entity.MemberEntity;
import com.delbot.danam.domain.member.repository.MemberRepository;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceLogic implements MemberService{

  private final MemberRepository memberRepository;
  // private final ModelMapper mapper;

  @Override
  public void join(MemberDTO memberDTO) {
    //
    MemberEntity memberEntity = MemberEntity.builder()
    .memberId(memberDTO.getMemberId())
    .memberPassword(memberDTO.getMemberPassword())
    .memberName(memberDTO.getMemberName())
    .memberNick(memberDTO.getMemberNick())
    .memberType(memberDTO.getMemberType())
    .memberGender(memberDTO.getMemberGender())
    .memberBirthDay(memberDTO.getMemberBirthDay())
    .memberPhone(memberDTO.getMemberPhone())
    .memberAddress(memberDTO.getMemberAddress())
    .memberEmail(memberDTO.getMemberEmail())
    .build();
    memberRepository.save(memberEntity);
  }

  @Override
  public List<MemberDTO> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public MemberDTO findMemberBySeq(Long seq) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findMemberBySeq'");
  }

  @Override
  public MemberDTO findMemberById(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findMemberById'");
  }

  @Override
  public MemberDTO findMemberByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findMemberByName'");
  }

  @Override
  public MemberDTO update(MemberDTO memberDTO) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Long seq) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public MemberDTO login(MemberDTO memberDTO) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'login'");
  }

  @Override
  public String idDuplicationCheck(String id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'idDuplicationCheck'");
  }
}
