package com.delbot.danam.domain.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.delbot.danam.domain.board.dto.BoardCommentRequestDTO;
import com.delbot.danam.domain.board.dto.BoardCommentResponseDTO;
import com.delbot.danam.domain.board.service.BoardCommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class BoardCommentController {
  //
  private final BoardCommentService boardCommentService;

  @PostMapping
  public ResponseEntity<List<BoardCommentResponseDTO>> writeComment(@RequestParam Long boardId, BoardCommentRequestDTO commentRequest, Model model, Authentication authentication) {
    //
    commentRequest.updateMemberName(authentication.getName());
    boardCommentService.saveComment(boardId, commentRequest);
    List<BoardCommentResponseDTO> commentResponseDTOList = boardCommentService.viewComments(boardId);
    return new ResponseEntity<>(commentResponseDTOList, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<List<BoardCommentResponseDTO>> updateComment(@PathVariable Long id, BoardCommentRequestDTO commentRequest, Model model, Authentication authentication) {
    //
    if(boardCommentService.checkMember(authentication.getName(), id).equals("OK")) {
      Long boardId = boardCommentService.updateComment(id, commentRequest);
      List<BoardCommentResponseDTO> commentResponseDTOList = boardCommentService.viewComments(boardId);
      return new ResponseEntity<>(commentResponseDTOList, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<List<BoardCommentResponseDTO>> deleteComment(@PathVariable Long id, Model model, Authentication authentication) {
    //
    if(boardCommentService.checkMember(authentication.getName(), id).equals("OK")) {
      Long boardId = boardCommentService.deleteComment(id);
      List<BoardCommentResponseDTO> commentResponseDTOList = boardCommentService.viewComments(boardId);
      return new ResponseEntity<>(commentResponseDTOList, HttpStatus.OK); 
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
