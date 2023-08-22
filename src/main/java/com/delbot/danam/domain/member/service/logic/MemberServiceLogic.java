package com.delbot.danam.domain.member.service.logic;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.repository.MemberRepository;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceLogic implements MemberService{
  //
  private final MemberRepository memberRepository;
  private final ModelMapper mapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void join(MemberDTO memberDTO) { 
    memberRepository.save(Member.dtoToEntity(memberDTO, passwordEncoder.encode(memberDTO.getPassword()))); 
  }

  @Override
  public List<MemberDTO> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public MemberDTO findMemberById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findMemberById'");
  }

  @Override
  public MemberDTO findMemberByUsername(String username) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findMemberByUsername'");
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
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public String usernameDuplicationCheck(String username) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'usernameDuplicationCheck'");
  }
}
