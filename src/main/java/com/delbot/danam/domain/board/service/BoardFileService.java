package com.delbot.danam.domain.board.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.delbot.danam.domain.board.dto.BoardFileDTO;

public interface BoardFileService {
  //
  public BoardFileDTO findBySavedName(String name);
  public void saveFile(MultipartFile file, Long id) throws IOException;
}
