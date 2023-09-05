// package com.delbot.danam.domain.board.controller;

// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.io.PrintWriter;
// import java.util.UUID;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.apache.commons.io.FilenameUtils;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.multipart.MultipartFile;

// @Controller
// public class CKEditorController {
//   //
//   @Value("${image.upload.path}")
//   private String uploadPath;
//   @Value("${resource.handler}")
//   private String resourceHandler;

//   @PostMapping("/board/write/imageUpload")
//   public void uploadImage(MultipartFile upload, HttpServletResponse res, HttpServletRequest req) {
//     //
//     OutputStream out = null;
//     PrintWriter printWriter = null;

//     res.setCharacterEncoding("utf-8");
//     res.setContentType("text/html;charset=utf-8");

//     try{
//       //
//       UUID uuid = UUID.randomUUID();
//       String extension = FilenameUtils.getExtension(upload.getOriginalFilename());

//       byte[] bytes = upload.getBytes();

//       String imageUploadPath = uploadPath + File.separator + uuid + "." + extension;

//       out = new FileOutputStream(imageUploadPath);
//       out.write(bytes);
//       out.flush();

//       printWriter = res.getWriter();
//       String callback = req.getParameter("CKEditorFuncNum");
//       String fileUrl = "/" + uuid + "." + extension;

//       printWriter.println("<script type='text/javascript'>"
//         + "window.parent.CKEDITOR.tool.callFunction("
//         + callback + "," + fileUrl + "','이미지를 업로드하였습니다.')"
//         + "</script>");

//       printWriter.flush();

//     } catch (IOException e) {
//       e.printStackTrace();
//     } finally {
//       try {
//         if(out != null) { out.close(); }
//         if(printWriter != null) { printWriter.close(); }
//       } catch (IOException e) { e.printStackTrace(); } 
//     }
//   }
  
// }
