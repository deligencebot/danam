package com.delbot.danam.domain.member.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.delbot.danam.domain.member.dto.MemberDTO;
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

    if(!memberService.nicknameDuplicationCheck(memberUpdateForm.getNickname()).equals("OK")) {
      bindingResult.rejectValue("nickname", "nicknameDuplication", "이미 별명이 존재합니다.");
      return "join";
    }

    MemberDTO updateMemberDTO = MemberDTO.updateFormToDTO(memberUpdateForm, id);
    System.out.println("MemberDTO = " + updateMemberDTO);
    memberService.update(updateMemberDTO);

    return "redirect:/member/detail/" + id;
  }
}
