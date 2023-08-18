package com.delbot.danam.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.delbot.danam.domain.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
  // @Modifying
  // @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.seq=:seq")
  // void updateHits(@Param("seq") Long seq);
}
