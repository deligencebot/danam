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

import com.delbot.danam.domain.board.dto.BoardImageDTO;
import com.delbot.danam.domain.board.entity.Board;
import com.delbot.danam.domain.board.entity.BoardImage;
import com.delbot.danam.domain.board.exception.NotFoundBoardException;
import com.delbot.danam.domain.board.exception.NotFoundBoardImageException;
import com.delbot.danam.domain.board.repository.BoardImageRepository;
import com.delbot.danam.domain.board.repository.BoardRepository;
import com.delbot.danam.domain.board.service.BoardImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardImagaServiceLogic implements BoardImageService{
  //
  @Value("${image.path}")
  private String fileDir;

  private final BoardRepository boardRepository;
  private final BoardImageRepository boardImageRepository;
  private final ModelMapper mapper;

  @Override
  public BoardImageDTO findBySavedName(String name) {
    return mapper.map(boardImageRepository.findBySavedName(name).orElseThrow(() 
    -> new NotFoundBoardImageException("해당 이미지가 없습니다.\nName : " + name)), BoardImageDTO.class);
  }

  @Override
  public void saveImage(MultipartFile file, Long id) throws IOException {
    //
    String originalName = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    String savedName = uuid + "." + extension;
    String savedPath = fileDir + savedName;

    Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException("해당 게시글을 찾을 수 없습니다.\nID : " + id));

    BoardImage boardImage = BoardImage.builder()
      .originalName(originalName)
      .savedName(savedName)
      .savedPath(savedPath)
      .board(board)
      .build();

    file.transferTo(new File(savedPath));
    boardImageRepository.save(boardImage);
    System.out.println("Save : " + originalName);
  }
}
