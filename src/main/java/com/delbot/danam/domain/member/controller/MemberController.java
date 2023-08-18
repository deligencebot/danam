package com.delbot.danam.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.dto.MemberJoinForm;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/join")
  public String joinForm() { return "join"; }

  @PostMapping("/join")
  public String join(@ModelAttribute MemberJoinForm memberJoinDTO) {
    System.out.println("MemberJoinDTO = " + memberJoinDTO);
    MemberDTO memberDTO = MemberDTO.joinFormToDTO(memberJoinDTO);
    System.out.println("MemberDTO = " + memberDTO);
    memberService.join(memberDTO);
    return "login";
  }

  // @GetMapping("/login")
  // public String loginForm() { return "login"; }

  // @PostMapping("/login")
  // public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
  //   MemberDTO loginResult = memberService.login(memberDTO);

  //   if (loginResult != null) {
  //     session.setAttribute(("loginSeq"), loginResult);
  //   }
  // }
  
}
