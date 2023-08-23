package com.delbot.danam.domain.member.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
  //
  private final MemberService memberService;

  @GetMapping("/join")
  public String joinForm(MemberJoinForm memberJoinForm) { return "join"; }

  @PostMapping("/join")
  public String join(@Valid MemberJoinForm memberJoinForm, BindingResult bindingResult) {
    //
    if(bindingResult.hasErrors()) {
      return "join";
    }

    if(!memberJoinForm.getPassword().equals(memberJoinForm.getPasswordCheck())) {
      bindingResult.rejectValue("passwordCheck", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
      return "join";
    }

    MemberDTO memberDTO = MemberDTO.formToDto(memberJoinForm);
    System.out.println("MemberDTO = " + memberDTO);
    memberService.join(memberDTO);
    return "redirect:/";
  }

  @GetMapping("/login")
  public String loginForm() { return "login"; }
}
