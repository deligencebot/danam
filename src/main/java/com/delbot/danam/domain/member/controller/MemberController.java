package com.delbot.danam.domain.member.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.dto.MemberJoinForm;
import com.delbot.danam.domain.member.dto.MemberUpdateForm;
import com.delbot.danam.domain.member.service.MemberService;
import com.delbot.danam.global.util.Script;

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

  @GetMapping("/detail/{id}")
  public String viewMemberDetail(@PathVariable Long id, Model model, Authentication authentication) {
    //
    String username = authentication.getName();
    Long authId = memberService.findMemberByUsername(username).getId();

    if(id != authId) {
      return Script.locationMsg("redirect:/", "잘못된 접근입니다", model);
    }

    MemberDTO memberDTO = memberService.findMemberById(id);
    model.addAttribute("member", memberDTO);
    return "detail";
  }

  @GetMapping("/detail/api/{id}")
  public String updateForm(@PathVariable Long id, MemberUpdateForm memberUpdateForm, Model model, Authentication authentication) { 
    //
    String username = authentication.getName();
    Long authId = memberService.findMemberByUsername(username).getId();

    if(id != authId) {
      return Script.locationMsg("redirect:/", "잘못된 접근입니다", model);
    }

    MemberDTO memberDTO = memberService.findMemberById(id);
    model.addAttribute("member", memberDTO);
    return "update"; 
  }

  @PutMapping("/detail/api/{id}")
  public String updateMember(@PathVariable Long id, @Valid MemberUpdateForm memberUpdateForm, BindingResult bindingResult, Model model) {
    //
    MemberDTO memberDTO = memberService.findMemberById(id);
    model.addAttribute("member", memberDTO);

    if(bindingResult.hasErrors()) {
      System.out.println("bindingResult = " + bindingResult.getAllErrors());
      return "update";
    }

    if(!memberService.updateCheck(id, memberUpdateForm.getPrePassword()).equals("OK")) {
      bindingResult.rejectValue("prePassword", "prePasswordIncorrect", "이전 비밀번호와 일치하지 않습니다.");
      return "update";
    }

    if(memberUpdateForm.getPassword().equals(memberUpdateForm.getPrePassword())) {
      bindingResult.rejectValue("password", "passwordIncorrect", "이전 비밀번호와 다른 비밀번호를 설정해주십시오.");
      return "update";
    }

    if(!memberUpdateForm.getPassword().equals(memberUpdateForm.getPasswordCheck())) {
      bindingResult.rejectValue("passwordCheck", "passwordIncorrect", "비밀번호가 일치하지 않습니다.");
      return "update";
    }

    MemberDTO updateMemberDTO = MemberDTO.updateFormToDTO(memberUpdateForm, id);
    System.out.println("MemberDTO = " + updateMemberDTO);
    memberService.update(updateMemberDTO);

    return "redirect:/member/detail/" + id;
  }
}
