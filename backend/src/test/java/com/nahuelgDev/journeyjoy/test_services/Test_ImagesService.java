package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Images;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.repositories.ImagesRepository;
import com.nahuelgDev.journeyjoy.services.ImagesService;

@ExtendWith(MockitoExtension.class)
class Test_ImagesService {

  @Mock private ImagesRepository imagesRepo;
  @InjectMocks private ImagesService imagesService;

  private MultipartFile file = mock(MultipartFile.class);
  private Images imageFromFile = new Images();

  @BeforeEach
  void setUp() throws IOException {
    when(file.getOriginalFilename()).thenReturn("image.jpg");
    when(file.getContentType()).thenReturn("image/jpeg");
    when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});

    imageFromFile = Images.builder()
      .id("1")
      .name("image.jpg")
      .contentType("image/jpeg")
      .data(new byte[]{1, 2, 3})
    .build();
  }

  @Test
  void add_returnSavedImageFromFile() throws IOException {
    Images expected = imageFromFile;

    when(imagesRepo.save(any(Images.class))).thenReturn(expected);

    Images actual = imagesService.add(file);

    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(imagesRepo).save(any(Images.class));
  }

  @Test
  void add_throwsEmptyFieldException() {
    MultipartFile nullFile = null;

    assertThrows(EmptyFieldException.class, () -> imagesService.add(nullFile));
    verify(imagesRepo, times(0)).save(any(Images.class));
  }

  @Test
  void update_returnsUpdatedImages() throws IOException {
    Images expected = imageFromFile;
    when(imagesRepo.findById("1")).thenReturn(Optional.of(expected));
    when(imagesRepo.save(any(Images.class))).thenReturn(expected);

    Images actual = imagesService.update("1", file);

    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(imagesRepo).save(any(Images.class));
    verify(imagesRepo).findById("1");
  }

  @Test
  void update_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> imagesService.update("", file));
    assertThrows(EmptyFieldException.class, () -> imagesService.update("1", null));
    verify(imagesRepo, times(0)).findById(anyString());
    verify(imagesRepo, times(0)).save(any(Images.class));
  }

  @Test
  void update_throwsDocumentNotFoundException() {
    when(imagesRepo.findById("99")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> imagesService.update("99", file));
    verify(imagesRepo, times(1)).findById("99");
    verify(imagesRepo, times(0)).save(any(Images.class));
  }
}
