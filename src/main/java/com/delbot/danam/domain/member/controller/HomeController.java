package com.delbot.danam.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String index() {
    System.out.println("Welcome");
    return "layout";
  }
}
