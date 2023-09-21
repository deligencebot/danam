package com.delbot.danam.domain.member.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.repository.MemberRepository;
import com.delbot.danam.domain.member.vo.MemberRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSecurityService implements UserDetailsService{
  //
  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //
    Member foundMember = this.memberRepository.findByUsername(username)
    .orElseThrow(() -> new UsernameNotFoundException("등록된 아이디가 없습니다."));

    log.info("Success find member {}", foundMember);

    List<GrantedAuthority> authorities = new ArrayList<>();
    if (foundMember.getRole().equals(MemberRole.Admin)) {
      authorities.add(new SimpleGrantedAuthority(MemberRole.Admin.getValue()));
    } else {
      authorities.add(new SimpleGrantedAuthority(MemberRole.Member.getValue()));
    }

    log.info("ID : {}", foundMember.getUsername());
    log.info("Password : {}", foundMember.getPassword());

    return User.builder()
    .username(foundMember.getUsername())
    .password(foundMember.getPassword())
    .authorities(authorities)
    .build();
  }
  
}
