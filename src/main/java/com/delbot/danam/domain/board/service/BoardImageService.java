package com.delbot.danam.domain.board.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.delbot.danam.domain.board.dto.BoardImageDTO;

public interface BoardImageService {
  //
  public BoardImageDTO findBySavedName(String name);
  public void saveImage(MultipartFile file, Long id) throws IOException;
}
