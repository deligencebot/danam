package com.delbot.danam.domain.board.service.logic;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.delbot.danam.domain.board.entity.Board;
import com.delbot.danam.domain.board.entity.BoardFile;
import com.delbot.danam.domain.board.exception.NoSuchBoardException;
import com.delbot.danam.domain.board.repository.BoardFileRepository;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardFileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFileServiceLogic implements BoardFileService{
  //
  @Value("${file.path}")
  private String fileDir;

  private final BoardRepository boardRepository;
  private final BoardFileRepository boardFileRepository;

  @Override
  public void saveFile(MultipartFile file, Long id) throws IOException {
    //
    String originalName = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    String savedName = uuid + "." + extension;
    String savedPath = fileDir + savedName;

    Board board = boardRepository.findById(id).orElseThrow(() -> new NoSuchBoardException("해당 게시글을 찾을 수 없습니다.\nID : " + id));

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
