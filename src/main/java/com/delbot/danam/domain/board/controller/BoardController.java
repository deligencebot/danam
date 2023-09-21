package com.delbot.danam.domain.board.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.delbot.danam.domain.board.dto.BoardCommentResponseDTO;
import com.delbot.danam.domain.board.dto.BoardDTO;
import com.delbot.danam.domain.board.dto.BoardFileDTO;
import com.delbot.danam.domain.board.service.*;
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
  private final BoardCommentService boardCommentService;

  @GetMapping("/{type}")
  public String searchList(@PathVariable Long type, @RequestParam(required = false) String condition, @RequestParam(required = false) String keyword, @PageableDefault(page = 1) Pageable pageable, Model model) {
    //
    if(condition != null && keyword != null) {
      if(condition.equals("title")) {
        System.out.println("Find By Title : " + keyword);
        Page<BoardDTO> boardList = boardService.searchTitle(type, keyword, pageable);
        model.addAttribute("boardList", boardList);
      } else if(condition.equals("titleContents")) {
        System.out.println("Find By Title&Contents : " + keyword);
        Page<BoardDTO> boardList = boardService.searchTitleAndContents(type, keyword, pageable);
        model.addAttribute("boardList", boardList);
      } else if(condition.equals("writer")) {
        System.out.println("Find By Writer : " + keyword);
        Page<BoardDTO> boardList = boardService.searchWriter(type, keyword, pageable);
        model.addAttribute("boardList", boardList);
      } else {
        return "redirect:/";
      }
    } else {
      System.out.println("Find All");
      Page<BoardDTO> boardList = boardService.getPage(type, pageable);
      model.addAttribute("boardList", boardList);
    }
    model.addAttribute("type", type.toString());
    return "board_list";
  }

  @GetMapping("/{type}/write")
  public String writeForm(@PathVariable Long type, BoardDTO boardDTO, Model model) {
    //
    model.addAttribute("type", type.toString());
    return "board_write";
  }

  @PostMapping("/{type}/write")
  public String wirte(@PathVariable Long type, @Valid BoardDTO boardDTO, BindingResult bindingResult, Model model, Authentication authentication) throws IOException {
    //
    if(bindingResult.hasErrors()) {
      System.out.println("bindingResult = " + bindingResult.getAllErrors());
      return "board_write";
    }

    model.addAttribute("type", type);
    String writer = memberService.findMemberByUsername(authentication.getName()).getNickname();

    BoardDTO newBoardDTO = BoardDTO.builder()
    .boardSequence(boardService.getLastSequence(1L))
    .boardType(type)
    .boardTitle(boardDTO.getBoardTitle())
    .boardWriter(writer)
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

    if(boardDTO.getBoardFile() != null && !FilenameUtils.getExtension(boardDTO.getBoardFile().get(0).getOriginalFilename()).isBlank()) {
      for(MultipartFile file : boardDTO.getBoardFile()) {
          boardFileService.saveFile(file, boardId);
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
  public String viewDetail(@PathVariable Long type, @PathVariable Long seq, BoardDTO boardDTO, Model model) {
    //
    boardService.updateHits(type, seq);
    BoardDTO foundBoard = boardService.findByTypeAndSequence(type, seq);
    List<BoardCommentResponseDTO> commentResponseDTOList = boardCommentService.viewComments(foundBoard.getId());
    model.addAttribute("board", foundBoard);
    model.addAttribute("commentList", commentResponseDTOList);

    return "board_detail";
  }

  @GetMapping("/{type}/{seq}/api")
  public String updateForm(@PathVariable Long type, @PathVariable Long seq, BoardDTO boardDTO, Model model, Authentication authentication) {
    //
    BoardDTO foundBoardDTO = boardService.findByTypeAndSequence(type, seq);
    model.addAttribute("board",foundBoardDTO);

    return foundBoardDTO.getBoardWriter().equals(authentication.getName()) ? "board_update" : Script.locationMsg("redirect:/board/" + type, "잘못된 접근입니다.", model);
  }

  @PutMapping("/{type}/{seq}/api")
  public String update(@PathVariable Long type, @PathVariable Long seq, BoardDTO boardDTO, Model model) throws IOException{
    //
    boardService.update(type, seq, boardDTO);
    return "redirect:/board/" + type + "/" + seq;
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

  @DeleteMapping("/{type}/{seq}/api")
  public String delete(@PathVariable Long type, @PathVariable Long seq, Model model, Authentication authentication) {
    //
    if(boardService.checkMember(type, seq, authentication.getName()).equals("OK")) {
      boardService.delete(type, seq);
      return "redirect:/board/" + type;
    } else {
      return "redirect:/board/" + type + "/" + seq;
    }
  }
}
