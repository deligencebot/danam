package com.delbot.danam.domain.member.service.logic;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.exception.NoSuchIdException;
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
    //
    memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
    memberRepository.save(mapper.map(memberDTO, Member.class));
    // memberRepository.save(Member.dtoToEntity(memberDTO, passwordEncoder.encode(memberDTO.getPassword()))); 
  }

  @Override
  public List<MemberDTO> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public MemberDTO findMemberById(Long id) {
    //
    Optional<Member> foundMember = memberRepository.findById(id);

    if (!foundMember.isPresent()) {
      throw new NoSuchIdException(String.format("Member(%s) is not found"));
    }

    return mapper.map(foundMember.get(), MemberDTO.class);
  }

  @Override
  public MemberDTO findMemberByUsername(String username) {
    //
    Optional<Member> foundMember = memberRepository.findByUsername(username);

    if (!foundMember.isPresent()) {
      throw new NoSuchIdException(String.format("Member(%s) is not found", foundMember.toString()));
    }

    return mapper.map(foundMember.get(), MemberDTO.class);
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
    //
    Optional<Member> foundMember = memberRepository.findByUsername(username);

    if(foundMember.isPresent()) {
      return "Duplication";
    } else {
      return "OK";
    }
  }

  @Override
  public String updateCheck(Long id, String password) {
    //
    Optional<Member> foundMember = memberRepository.findById(id);

    if(passwordEncoder.matches(password, foundMember.get().getPassword())) {
      System.out.println("비밀번호 일치 : " + password);
      return "OK";
    } else {
      System.out.println("비밀번호 불일치 : " + password);
      return "DIFF";
    }
  }
}
