package com.delbot.danam.domain.board.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface BoardFileService {
  //
  public void saveFile(MultipartFile file, Long id) throws IOException;
}
