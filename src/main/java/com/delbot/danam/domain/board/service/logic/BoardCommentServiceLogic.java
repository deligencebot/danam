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
import com.delbot.danam.domain.board.exception.NoSuchBoardException;
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
  public void saveComment(BoardCommentRequestDTO commentRequestDTO, Long type, Long seq) {
    //
    Member foundMember = memberRepository.findById(commentRequestDTO.getMemeberId())
      .orElseThrow(() -> new NoSuchMemberException("해당 멤버를 찾지 못했습니다.\nID : " + commentRequestDTO.getMemeberId()));
    Board foundBoard = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
      .orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq));

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
  public void delete(Long id) {
    //
    BoardComment comment = boardCommentRepository.findBoardCommentByIdWithParent(id)
      .orElseThrow(() -> new NotFoundCommentException("해당 댓글을 찾을 수 없습니다.\nId : " + id));
    if(comment.getChildren().size() != 0) {
      comment.updateIsDeleted(1L);
    } else {
      boardCommentRepository.delete(getDeletableAncestorComment(comment));
    }
  }

  private BoardComment getDeletableAncestorComment(BoardComment comment) {
    BoardComment parent = comment.getParent();
    if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == 1L) {
      return getDeletableAncestorComment(parent);
    }
    return comment;
  }
}
