package com.delbot.danam.domain.member.service.logic;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.exception.NoSuchMemberException;
import com.delbot.danam.domain.member.repository.MemberRepository;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceLogic implements MemberService{
  //
  private final MemberRepository memberRepository;
  private final ModelMapper mapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void join(MemberDTO memberDTO) { 
    //
    memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
    memberRepository.save(mapper.map(memberDTO, Member.class));
  }

  @Override
  public List<MemberDTO> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public MemberDTO findMemberById(Long id) {
    //
    return mapper.map(memberRepository.findById(id)
    .orElseThrow(() -> new NoSuchMemberException("해당 멤버를 찾지 못했습니다.\nID : " + id)), MemberDTO.class);
  }

  @Override
  public MemberDTO findMemberByUsername(String username) {
    //
    return mapper.map(memberRepository.findByUsername(username)
    .orElseThrow(() -> new NoSuchMemberException("해당 멤버를 찾지 못했습니다.\nUsername : " + username)), MemberDTO.class);
  }

  @Override
  public MemberDTO findMemberByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findMemberByName'");
  }

  @Override
  public void update(MemberDTO memberDTO) {
    //
    String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
    Member foundMember = memberRepository.findById(memberDTO.getId()).get();

    if(memberDTO.getNickname().isBlank()) {
      memberDTO.setNickname(foundMember.getNickname());
    }

    if(memberDTO.getPhoneNumber().isBlank()) {
      memberDTO.setPhoneNumber(foundMember.getPhoneNumber());
    }

    if(memberDTO.getAddress().isBlank()) {
      memberDTO.setAddress(foundMember.getAddress());
    }

    if(memberDTO.getEmail().isBlank()) {
      memberDTO.setEmail(foundMember.getEmail());
    }

    foundMember.update(encodedPassword, memberDTO.getNickname(), memberDTO.getPhoneNumber(), memberDTO.getAddress(), memberDTO.getEmail());
    memberRepository.save(foundMember);
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public String usernameDuplicationCheck(String username) {
    return memberRepository.findByUsername(username).isPresent() ? "Duplication" : "OK";
  }

  @Override
  public String nicknameDuplicationCheck(String nickname) {
    return memberRepository.findByNickname(nickname).isPresent() ? "Dulication" : "OK";
  }

  @Override
  public String updateCheck(Long id, String password) {
    //
    Member foundMember = memberRepository.findById(id)
    .orElseThrow(() -> new NoSuchMemberException("해당 멤버를 찾지 못했습니다.\nID : " + id));
    return passwordEncoder.matches(password, foundMember.getPassword()) ? "OK" : "DIFF";
  }
}
