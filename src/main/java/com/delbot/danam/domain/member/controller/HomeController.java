package com.delbot.danam.domain.member.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
  //
  private final MemberService memberService;

  @GetMapping("/")
  public String home(Model model, Authentication authentication) {
    //
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);
      model.addAttribute("member", memberDTO);
    }

    System.out.println("Welcome");

    return "layout";
  }
}
