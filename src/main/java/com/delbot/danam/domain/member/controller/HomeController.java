package com.delbot.danam.domain.member.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.dto.MemberJoinForm;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
  //
  private final MemberService memberService;

  @GetMapping("/")
  public String home(Model model) {
    //
    System.out.println("Welcome");

    return "layout";
  }

  @GetMapping("/join")
  public String joinForm(MemberJoinForm memberJoinForm) { return "join"; }

  @PostMapping("/join")
  public String join(@Valid MemberJoinForm memberJoinForm, BindingResult bindingResult) {
    //
    if(bindingResult.hasErrors()) {
      System.out.println("bindingResult = " + bindingResult.getAllErrors());
      return "join";
    }

    if(!memberService.usernameDuplicationCheck(memberJoinForm.getUsername()).equals("OK")) {
      bindingResult.rejectValue("username", "usernameDuplication", "이미 아아디가 존재합니다.");
      return "join";
    }

    if(!memberJoinForm.getPassword().equals(memberJoinForm.getPasswordCheck())) {
      bindingResult.rejectValue("passwordCheck", "passwordInCorrect", "비밀번호가 일치하지 않습니다.");
      return "join";
    }

    MemberDTO memberDTO = MemberDTO.joinFormToDTO(memberJoinForm);
    System.out.println("MemberDTO = " + memberDTO);
    memberService.join(memberDTO);

    return "redirect:/";
  }

  @GetMapping("/login")
  public String loginForm() { return "login"; }
}
