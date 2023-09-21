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
import com.delbot.danam.domain.board.exception.NotFoundBoardException;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceLogic implements BoardService {
  //
  static final int PAGE_LIMIT = 3;

  private final BoardRepository boardRepository;
  private final ModelMapper mapper;

  @Override
  @Transactional
  public BoardDTO findByTypeAndSequence(Long type, Long seq) {
    //
    Board board = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
      .orElseThrow(() -> new NotFoundBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq));

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
  public void update(Long type, Long seq, BoardDTO boardDTO) { 
    Board foundBoard = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
      .orElseThrow(() -> new NotFoundBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq));
    foundBoard.updateBoard(boardDTO);
    boardRepository.save(foundBoard);
  }

  @Override
  @Transactional
  public void delete(Long type, Long seq) { 
    //
    Long id = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
      .orElseThrow(() -> new NotFoundBoardException("해당 게시글을 찾을 수 없습니다.\nType : " + type + "\nSequence : " + seq)).getId();
    boardRepository.deleteById(id); 
  }

  @Override
  public Page<BoardDTO> getPage(Long type, Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    Page<Board> boardPages = boardRepository.findByBoardType(type, PageRequest.of(page, PAGE_LIMIT, Sort.Direction.DESC, "boardSequence"));
    return mapToDTO(boardPages);
  }

  @Override
  public Page<BoardDTO> searchTitle(Long type, String keyword, Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    Page<Board> boardPages = boardRepository.findByBoardTypeAndBoardTitleContaining(type, keyword, PageRequest.of(page, PAGE_LIMIT, Sort.Direction.DESC, "boardSequence"));
    return mapToDTO(boardPages);
  }

  @Override
  public Page<BoardDTO> searchTitleAndContents(Long type, String keyword, Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    Page<Board> boardPages = boardRepository.findByTitleAndContents(type, keyword, PageRequest.of(page, PAGE_LIMIT, Sort.Direction.DESC, "boardSequence"));
    return mapToDTO(boardPages);
  }

  @Override
  public Page<BoardDTO> searchWriter(Long type, String keyword, Pageable pageable) {
    //
    int page = pageable.getPageNumber() - 1;
    Page<Board> boardPages = boardRepository.findByBoardTypeAndBoardWriterContaining(type, keyword, PageRequest.of(page, PAGE_LIMIT, Sort.Direction.DESC, "boardSequence"));
    return mapToDTO(boardPages);
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
    .orElseThrow(() -> new NotFoundBoardException("Not Found Board.\nType : " + type + "\nSequence : " + seq));
    boardRepository.updateHits(board.getId());
  }

  @Override
  public String checkMember(Long type, Long seq, String username) {
    //
    String writer = boardRepository.findByBoardTypeAndBoardSequence(type, seq)
    .orElseThrow(() -> new NotFoundBoardException("Not Found Board.\nType : " + type + "\nSequence : " + seq)).getBoardWriter();
    if(writer.equals(username)) return "OK";
    else return "NO";
  }

  private Page<BoardDTO> mapToDTO(Page<Board> boardPages) {
    //
    Page<BoardDTO> boardDTOPages = boardPages.map(board -> {
      return new BoardDTO(
        board.getId(),
        board.getBoardSequence(),
        board.getBoardType(),
        board.getBoardTitle(),
        board.getBoardWriter(),
        board.getBoardHits(),
        board.getCreatedTime()
        );
    });
    return boardDTOPages;
  }
}
