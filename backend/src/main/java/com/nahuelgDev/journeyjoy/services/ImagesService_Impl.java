package com.nahuelgDev.journeyjoy.services;

import static com.nahuelgDev.journeyjoy.utilities.Verifications.checkFieldsHasContent;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Images;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.repositories.ImagesRepository;
import com.nahuelgDev.journeyjoy.services.interfaces.ImagesService_I;
import com.nahuelgDev.journeyjoy.utilities.Verifications.Field;

@Service
public class ImagesService_Impl implements ImagesService_I {
  private final ImagesRepository imageRepo;

  public ImagesService_Impl(ImagesRepository imageRepo) {
    this.imageRepo = imageRepo;
  }

  @Override
  public Images getById(String id) {
    checkFieldsHasContent(new Field("id", id));

    return imageRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("imagen", id, "id")
    );
  }

  @Override
  public Images add(MultipartFile file) throws IOException {
    checkFieldsHasContent(new Field("archivo", file));

    Images image = new Images();
    image.setName(file.getOriginalFilename());
    image.setContentType(file.getContentType());
    image.setData(file.getBytes());
    
    return imageRepo.save(image);
  }
  
  @Override
  public Images update(String id, MultipartFile file) throws IOException {
    checkFieldsHasContent(new Field("archivo", file), new Field("id", id));

    Images image = imageRepo.findById(id).orElseThrow(
      () -> new DocumentNotFoundException("imagen", id, "id")
    );
    image.setName(file.getOriginalFilename());
    image.setContentType(file.getContentType());
    image.setData(file.getBytes());
    
    return imageRepo.save(image);    
  }
}
