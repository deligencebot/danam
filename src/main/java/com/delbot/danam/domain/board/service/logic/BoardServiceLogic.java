package com.delbot.danam.domain.board.service.logic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delbot.danam.domain.board.dto.BoardDTO;
import com.delbot.danam.domain.board.entity.Board;
import com.delbot.danam.domain.board.entity.BoardFile;
import com.delbot.danam.domain.board.entity.BoardImage;
import com.delbot.danam.domain.board.exception.NoSuchBoardException;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardService;
import com.delbot.danam.domain.member.entity.Member;
import com.delbot.danam.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceLogic implements BoardService {
  //
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;
  private final ModelMapper mapper;

  @Override
  @Transactional
  public BoardDTO findByTypeAndSequence(Long type, Long seq) {
    //
    Board board = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
    .orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq));

    BoardDTO boardDTO = BoardDTO.entityToDTO(board);

    if (board.getBoardImageList() != null && !board.getBoardImageList().isEmpty()) {
    List<String> originalImageNameList = board.getBoardImageList().stream()
            .map(BoardImage::getOriginalName)
            .collect(Collectors.toList());

    List<String> savedImageNameList = board.getBoardImageList().stream()
            .map(BoardImage::getSavedName)
            .collect(Collectors.toList());

    boardDTO.setOriginalImageName(originalImageNameList);
    boardDTO.setSavedImageName(savedImageNameList);
    }

    if (board.getBoardFileList() != null && !board.getBoardFileList().isEmpty()) {
        List<String> originalFileNameList = board.getBoardFileList().stream()
                .map(BoardFile::getOriginalName)
                .collect(Collectors.toList());

        List<String> savedFileNameList = board.getBoardFileList().stream()
                .map(BoardFile::getSavedName)
                .collect(Collectors.toList());

        boardDTO.setOriginalFileName(originalFileNameList);
        boardDTO.setSavedFileName(savedFileNameList);
    }

    return boardDTO;
  }

  @Override
  public Long write(BoardDTO boardDTO) { return boardRepository.save(mapper.map(boardDTO, Board.class)).getId(); }
    
  @Override
  @Transactional
  public void update(BoardDTO boardDTO) { boardRepository.save(mapper.map(boardDTO, Board.class)); }

  @Override
  @Transactional
  public void delete(Long id) { boardRepository.deleteById(id); }

  @Override
  public Page<BoardDTO> getPage(Long type, Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    int pageLimit = 3;
    Page<Board> boardPages = boardRepository.findByBoardType(type, PageRequest.of(page, pageLimit, Sort.Direction.DESC, "boardSequence"));

    Page<BoardDTO> boardDTOPages = boardPages.map(board -> {
      String writerNickname = memberRepository.findByUsername(board.getBoardWriter())
      .map(Member::getNickname)
      .orElse(board.getBoardWriter());

      return new BoardDTO(
        board.getId(),
        board.getBoardSequence(),
        board.getBoardType(),
        board.getBoardTitle(),
        writerNickname,
        board.getBoardHits(),
        board.getCreatedTime()
        );
    });

    return boardDTOPages;
  }

  @Override
  public Long getLastSequence(Long type) {
    //
    return boardRepository.findByBoardType(type)
    .stream()
    .map(Board::getBoardSequence)
    .max(Comparator.naturalOrder())
    .orElse(0L) + 1L;
  }

  @Override
  @Transactional
  public void updateHits(Long type, Long seq) {
    //
    Board board = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
    .orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq));
    boardRepository.updateHits(board.getId());
  }
}
