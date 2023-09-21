package com.delbot.danam.domain.board.service.logic;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.delbot.danam.domain.board.dto.BoardFileDTO;
import com.delbot.danam.domain.board.entity.Board;
import com.delbot.danam.domain.board.entity.BoardFile;
import com.delbot.danam.domain.board.exception.NotFoundBoardException;
import com.delbot.danam.domain.board.exception.NotFoundBoardFileException;
import com.delbot.danam.domain.board.repository.BoardFileRepository;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFileServiceLogic implements BoardFileService{
  //
  @Value("${file.path}")
  private String imageDir;

  private final BoardRepository boardRepository;
  private final BoardFileRepository boardFileRepository;
  private final ModelMapper mapper;

  @Override
  public BoardFileDTO findBySavedName(String name) {
    //
    return mapper.map(boardFileRepository.findBySavedName(name).orElseThrow(()
      -> new NotFoundBoardFileException("해당 파일이 없습니다.\nName : " + name)), BoardFileDTO.class);
  }

  @Override
  public void saveFile(MultipartFile file, Long id) throws IOException {
    //
    String originalName = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    String savedName = uuid + "." + extension;
    String savedPath = imageDir + savedName;

    Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException("해당 게시글을 찾을 수 없습니다.\nID : " + id));

    BoardFile boardFile = BoardFile.builder()
      .originalName(originalName)
      .savedName(savedName)
      .savedPath(savedPath)
      .board(board)
      .build();

    file.transferTo(new File(savedPath));
    boardFileRepository.save(boardFile);
    System.out.println("Save : " + originalName);
  }
}
