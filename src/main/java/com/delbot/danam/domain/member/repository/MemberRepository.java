package com.delbot.danam.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delbot.danam.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  //
  Optional<Member> findByUsername(String username);
  Optional<Member> findByNickname(String nickname);
}
