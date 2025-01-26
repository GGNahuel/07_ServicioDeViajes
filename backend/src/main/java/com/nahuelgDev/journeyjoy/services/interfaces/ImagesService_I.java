package com.nahuelgDev.journeyjoy.services.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Images;

public interface ImagesService_I {
  Images getById(String id);
  Images add(MultipartFile file) throws IOException;
  Images update(String id, MultipartFile file) throws IOException;
}
