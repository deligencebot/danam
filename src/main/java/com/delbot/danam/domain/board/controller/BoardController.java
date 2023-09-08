package com.delbot.danam.domain.board.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.delbot.danam.domain.board.dto.BoardDTO;
import com.delbot.danam.domain.board.dto.BoardFileDTO;
import com.delbot.danam.domain.board.service.BoardFileService;
import com.delbot.danam.domain.board.service.BoardImageService;
import com.delbot.danam.domain.board.service.BoardService;
import com.delbot.danam.domain.member.dto.MemberDTO;
import com.delbot.danam.domain.member.service.MemberService;
import com.delbot.danam.global.util.Script;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  //
  private final MemberService memberService;
  private final BoardService boardService;
  private final BoardFileService boardFileService;
  private final BoardImageService boardImageService;

  @GetMapping("/{type}")
  public String listForm(@PathVariable Long type, @PageableDefault(page = 1) Pageable pageable, Model model, Authentication authentication) {
    //
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);
      model.addAttribute("member", memberDTO);
    }

    Page<BoardDTO> boardList = boardService.getPage(type, pageable);
    model.addAttribute("type", type.toString());
    model.addAttribute("boardList", boardList);

    return "board_list";
  }

  @GetMapping("/{type}/write")
  public String writeForm(@PathVariable Long type, BoardDTO boardDTO, Model model, Authentication authentication) {
    //
    String username = authentication.getName();
    MemberDTO memberDTO = memberService.findMemberByUsername(username);
    model.addAttribute("member", memberDTO);

    model.addAttribute("type", type.toString());
    return "board_write";
  }

  @PostMapping("/{type}/write")
  public String wirte(@PathVariable Long type, BoardDTO boardDTO, Model model, Authentication authentication) throws IOException {
    //
    String username = authentication.getName();
    MemberDTO memberDTO = memberService.findMemberByUsername(username);
    model.addAttribute("member", memberDTO);

    model.addAttribute("type", type);

    BoardDTO newBoardDTO = BoardDTO.builder()
    .boardSequence(boardService.getLastSequence(1L))
    .boardType(type)
    .boardTitle(boardDTO.getBoardTitle())
    .boardWriter(username)
    .boardContents(boardDTO.getBoardContents())
    .boardHits(0L)
    .boardIsModified(0L)
    .boardIsNotice(0L)
    .boardIsCommentable(0L)
    .build();
    Long boardId = boardService.write(newBoardDTO);

    if(boardDTO.getBoardImage() != null && !boardDTO.getBoardImage().isEmpty()) {
      for(MultipartFile file : boardDTO.getBoardImage()) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.isBlank()) {
          boardImageService.saveImage(file, boardId);
          System.out.println(extension);
        }
      }
    }

    if(boardDTO.getBoardFile() != null && !boardDTO.getBoardFile().isEmpty()) {
      for(MultipartFile file : boardDTO.getBoardFile()) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.isBlank()) {
          boardFileService.saveFile(file, boardId);
          System.out.println(extension);
        }
      }
    }
    
    return new StringBuilder()
    .append("redirect:/board/")
    .append(type)
    .append("/")
    .append(newBoardDTO.getBoardSequence())
    .toString();
  }

  @GetMapping("/{type}/{seq}")
  public String viewDetail(@PathVariable Long type, @PathVariable Long seq, BoardDTO boardDTO, Model model, Authentication authentication) {
    //
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      MemberDTO memberDTO = memberService.findMemberByUsername(username);
      model.addAttribute("member", memberDTO);
    }

    boardService.updateHits(type, seq);
    BoardDTO foundBoard = boardService.findByTypeAndSequence(type, seq);
    foundBoard.setBoardWriterNick(memberService.findMemberByUsername(foundBoard.getBoardWriter()).getNickname()); 
    model.addAttribute("board", foundBoard);

    return "/board_detail";
  }

  @GetMapping("/{type}/{seq}/api")
  public String updateForm(@PathVariable Long type, @PathVariable Long seq, BoardDTO boardDTO, Model model, Authentication authentication) {
    //
    String username = authentication.getName();
    MemberDTO memberDTO = memberService.findMemberByUsername(username);
    model.addAttribute("member", memberDTO);

    BoardDTO foundBoardDTO = boardService.findByTypeAndSequence(type, seq);
    model.addAttribute("board",foundBoardDTO);

    return foundBoardDTO.getBoardWriter().equals(username) ? "board_update" : Script.locationMsg("redirect:/board/" + type, "잘못된 접근입니다.", model);
  }

  @PutMapping("/{type}/{seq}/api")
  public String update(@PathVariable Long type, @PathVariable Long seq, BoardDTO boardDTO, Model model, Authentication authentication) {
    //
    String username = authentication.getName();
    MemberDTO memberDTO = memberService.findMemberByUsername(username);
    model.addAttribute("member", memberDTO);

    BoardDTO foundBoardDTO = boardService.findByTypeAndSequence(type, seq);
    foundBoardDTO.setBoardTitle(boardDTO.getBoardTitle());
    foundBoardDTO.setBoardContents(boardDTO.getBoardContents());
    foundBoardDTO.setBoardIsModified(1L);
    boardService.update(foundBoardDTO);
    model.addAttribute("board",foundBoardDTO);
    
    return "board_detail";
  }

  @GetMapping("/file/{file}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String file) throws MalformedURLException {
    //
    BoardFileDTO fileDTO = boardFileService.findBySavedName(file);
    UrlResource resource = new UrlResource("file:" + fileDTO.getSavedPath());
    String encodedFileName = UriUtils.encode(fileDTO.getOriginalName(), StandardCharsets.UTF_8);
    String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
  }
}
