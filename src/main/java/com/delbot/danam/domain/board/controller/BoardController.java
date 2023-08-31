package com.delbot.danam.domain.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.delbot.danam.domain.board.dto.BoardDTO;
import com.delbot.danam.domain.board.service.BoardService;
import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

  private final MemberService memberService;
  private final BoardService boardService;

  @GetMapping
  public String boardForm(Model model, Authentication authentication) {
    //
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);
      model.addAttribute("member", memberDTO);
    }

    List<BoardDTO> boardList = boardService.findAll();
    model.addAttribute("boardList", boardList);

    return "board_list";
  }

  @GetMapping("/write")
  public String writeForm(BoardDTO boardDTO, Model model, Authentication authentication) {
    //
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);
      model.addAttribute("member", memberDTO);
    }

    return "board_write";
  }

  @PostMapping("/write")
  public String wirte(BoardDTO boardDTO, Model model, Authentication authentication) throws IOException {
    //
    String username = authentication.getName();
    MemberDTO memberDTO = memberService.findMemberByUsername(username);
    model.addAttribute("member", memberDTO);

    BoardDTO board = BoardDTO.builder()
    .boardSequence(boardService.getLastSequence(1L))
    .boardType(1L)
    .boardTitle(boardDTO.getBoardTitle())
    .boardWriter(username)
    .boardContents(boardDTO.getBoardContents())
    .boardHits(0L)
    .boardIsModified(0L)
    .boardIsNotice(0L)
    .boardIsCommentable(0L)
    .build();

    boardService.write(board);

    return "redirect:/board";
  }

  @GetMapping("/{id}")
  public String viewDetail(@PathVariable Long id, BoardDTO boardDTO, Model model, Authentication authentication) {
        //
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);
      model.addAttribute("member", memberDTO);
    }

    boardService.updateHits(id);
    BoardDTO board = boardService.findById(id);
    model.addAttribute("board", board);

    return "/board_detail";
  }

  // @GetMapping("/{type}")
  // public String listForm(@PathVariable Long type, @PageableDefault(page = 1) Pageable pageable, Model model) {
  //   //
  //   Page<BoardDTO> boardList = boardService.paging(pageable);
  //   model.addAttribute("boardList", boardList);

  //   return "board_list";
  // }

}
