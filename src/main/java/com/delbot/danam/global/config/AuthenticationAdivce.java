package com.delbot.danam.global.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAdivce {
  //
  private final MemberService memberService;

  @Before("execution(* com.delbot.danam.domain.member.controller.HomeController.home(..)) || execution(* com.delbot.danam.domain.board.controller.BoardController.*(..))")
  public void addMemberInfoToModel(JoinPoint joinPoint) {
    //
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(authentication != null && !authentication.getName().equals("anonymousUser")) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);

      for (Object arg : joinPoint.getArgs()) {
        if(arg instanceof Model) {
          ((Model) arg).addAttribute("member", memberDTO);
          break;
        }
      }
    }
  }
}
