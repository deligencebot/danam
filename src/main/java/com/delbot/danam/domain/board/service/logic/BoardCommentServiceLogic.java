package com.delbot.danam.domain.board.service.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delbot.danam.domain.board.dto.BoardCommentRequestDTO;
import com.delbot.danam.domain.board.dto.BoardCommentResponseDTO;
import com.delbot.danam.domain.board.entity.Board;
import com.delbot.danam.domain.board.entity.BoardComment;
import com.delbot.danam.domain.board.exception.NotFoundBoardCommentException;
import com.delbot.danam.domain.board.exception.NotFoundCommentException;
import com.delbot.danam.domain.board.repository.BoardCommentRepository;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardCommentService;
import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.exception.NoSuchMemberException;
import com.delbot.danam.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardCommentServiceLogic implements BoardCommentService{
  //
  private final BoardCommentRepository boardCommentRepository;
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public void saveComment(Long boardId, BoardCommentRequestDTO commentRequestDTO) {
    //
    Member foundMember = memberRepository.findByUsername(commentRequestDTO.getMemeberName())
      .orElseThrow(() -> new NoSuchMemberException("해당 멤버를 찾지 못했습니다.\nID : " + commentRequestDTO.getMemeberName()));
    Board foundBoard = boardRepository.findById(boardId)
      .orElseThrow(() -> new NotFoundBoardCommentException("해당 게시글을 찾을 수 없습니다.\nId : " + boardId));

    BoardComment comment = BoardComment.builder()
      .commentContents(commentRequestDTO.getCommentContents())
      .member(foundMember)
      .isDeleted(0L)
      .board(foundBoard)
      .build();

    if(commentRequestDTO.getParentId() != null) {
      BoardComment parentComment = boardCommentRepository.findById(commentRequestDTO.getParentId())
        .orElseThrow(() -> new NotFoundCommentException("해당 댓글을 찾을 수 없습니다.\nId : " + commentRequestDTO.getParentId()));
        comment.updateParent(parentComment);
    }

    boardCommentRepository.save(comment);
  }

  @Override
  @Transactional
  public Long updateComment(Long id, BoardCommentRequestDTO commentRequestDTO) {
    //
    BoardComment comment = boardCommentRepository.findById(id)
      .orElseThrow(() -> new NotFoundCommentException("해당 댓글을 찾을 수 없습니다.\nId : " + id));
    
    comment.updateComment(commentRequestDTO.getCommentContents());

    return boardCommentRepository.save(comment).getBoard().getId();
  }

  @Override
  @Transactional
  public List<BoardCommentResponseDTO> viewComments(Long boardId) {
    //
    List<BoardComment> comments = boardCommentRepository.findByBoardId(boardId);

    List<BoardCommentResponseDTO> commentResponseDTOList = new ArrayList<>();
    Map<Long, BoardCommentResponseDTO> commentDTOHashMap = new HashMap<>();

    comments.forEach(comment -> {
      BoardCommentResponseDTO commentResponseDTO = BoardCommentResponseDTO.mapToDTO(comment);
      commentDTOHashMap.put(commentResponseDTO.getId(), commentResponseDTO);
      if(comment.getParent() != null) commentDTOHashMap.get(comment.getParent().getId()).getChildren().add(commentResponseDTO);
      else commentResponseDTOList.add(commentResponseDTO);
    });
    return commentResponseDTOList;
  }

  @Override
  @Transactional
  public Long deleteComment(Long id) {
    //
    BoardComment comment = boardCommentRepository.findBoardCommentByIdWithParent(id)
      .orElseThrow(() -> new NotFoundCommentException("해당 댓글을 찾을 수 없습니다.\nId : " + id));
    Long boardId = comment.getBoard().getId();
    if(comment.getChildren().size() != 0) {
      comment.updateIsDeleted(1L);
    } else {
      boardCommentRepository.delete(getDeletableAncestorComment(comment));
    }

    return boardId;
  }

  private BoardComment getDeletableAncestorComment(BoardComment comment) {
    //
    BoardComment parent = comment.getParent();
    if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == 1L) {
      return getDeletableAncestorComment(parent);
    }
    return comment;
  }

  @Override
  public String checkMember(String memberName, Long id) {
    //
    BoardComment comment = boardCommentRepository.findById(id)
      .orElseThrow(() -> new NotFoundCommentException("해당 댓글을 찾을 수 없습니다.\nId : " + id));
    System.out.println("Compare " + memberName + comment.getMember().getUsername());
    if(comment.getMember().getUsername().equals(memberName)) return "OK";
    else return "NO";
  }
}



