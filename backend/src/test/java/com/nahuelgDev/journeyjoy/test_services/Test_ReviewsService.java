package com.nahuelgDev.journeyjoy.test_services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.nahuelgDev.journeyjoy.collections.Images;
import com.nahuelgDev.journeyjoy.collections.Reviews;
import com.nahuelgDev.journeyjoy.exceptions.DocumentNotFoundException;
import com.nahuelgDev.journeyjoy.exceptions.EmptyFieldException;
import com.nahuelgDev.journeyjoy.repositories.ReviewsRepository;
import com.nahuelgDev.journeyjoy.services.ImagesService;
import com.nahuelgDev.journeyjoy.services.ReviewsService;

@ExtendWith(MockitoExtension.class)
public class Test_ReviewsService {

  @Mock
  private ReviewsRepository repository;

  @Mock
  private ImagesService imagesService;

  @InjectMocks
  private ReviewsService service;

  private Reviews review1, review2;
  private Images image;

  @BeforeEach
  void setUp() {
    image = Images.builder().id("1").contentType("image/jpeg").name("foto.jpg").build();
    
    review1 = Reviews.builder()
      .id("1")
      .userName("juan")
      .rating(2.5)
      .userImage(image)
    .build();
    review2 = Reviews.builder()
      .id("2")
      .userName("pedro")
      .rating(2.5)
      .userImage(image)
    .build();
  }

  @Test
  void getAll_returnsExpectedList() {
    List<Reviews> expected = List.of(review1, review2);
    when(repository.findAll()).thenReturn(expected);

    List<Reviews> actual = service.getAll();

    assertIterableEquals(expected, actual);
    verify(repository).findAll();
  }

  @Test
  void getById_returnsExpected() {
    when(repository.findById("1")).thenReturn(Optional.of(review1));

    Reviews actual = service.getById("1");

    assertEquals(review1, actual);
    verify(repository).findById("1");
  }

  @Test
  void getById_throwsEmptyFieldException() {
    assertThrows(EmptyFieldException.class, () -> service.getById(""));
    verify(repository, times(0)).findById(anyString());
  }

  @Test 
  void getById_throwsDocumentNotFoundException() {
    when(repository.findById("4")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.getById("4"));
    verify(repository).findById("4");
  }

  @Test
  void create_returnsExpected() throws IOException {
    Reviews input = Reviews.builder()
      .userName(review1.getUserName())
      .rating(review1.getRating())
    .build();
    when(repository.save(input)).thenReturn(review1);

    MultipartFile file = mock(MultipartFile.class);
    when(imagesService.add(file)).thenReturn(image);

    Reviews actual = service.create(input, file);

    assertEquals(review1, actual);
    verify(repository).save(input);
    verify(imagesService).add(file);
  }

  @Test
  void create_throwsEmptyFieldException() throws IOException {
    Reviews withoutAutor = Reviews.builder().rating(2.5).build();
    Reviews emptyAutor = Reviews.builder().userName("").rating(2.0).build();
    Reviews withoutRating = Reviews.builder().userName("juan").build();
    MultipartFile file = mock(MultipartFile.class);

    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutAutor, file)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(emptyAutor, file)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutRating, file)),
      () -> assertThrows(EmptyFieldException.class, () -> service.create(withoutRating, null))
    );
    verify(imagesService, times(0)).add(file);
    verify(repository, times(0)).save(any(Reviews.class));
  }

  @Test
  void update_returnsExpected() throws IOException {
    Reviews expected = review1;
    expected.setUserName("ivan");
    when(repository.findById("1")).thenReturn(Optional.of(review1));
    when(repository.save(expected)).thenReturn(expected);

    MultipartFile file = mock(MultipartFile.class);
    Images updateImg = image;
    updateImg.setName("newPicture.jpg");
    when(imagesService.update("1", file)).thenReturn(updateImg);

    Reviews actual = service.update(expected, file);

    assertEquals(expected.getUserName(), actual.getUserName());
    assertEquals(updateImg, actual.getUserImage());
    verify(repository).findById("1");
    verify(repository).save(expected);
    verify(imagesService).update("1", file);
  }

  @Test
  void update_throwsEmptyFieldException() throws IOException {
    Reviews withoutId = Reviews.builder().id(null).userImage(image).build();
    Reviews emptyId = Reviews.builder().id("").userImage(image).build();
    Reviews withoutImage = Reviews.builder().id("1").userImage(null).build();

    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.update(withoutId, mock(MultipartFile.class))),
      () -> assertThrows(EmptyFieldException.class, () -> service.update(emptyId, mock(MultipartFile.class))),
      () -> assertThrows(EmptyFieldException.class, () -> service.update(withoutImage, mock(MultipartFile.class))),
      () -> assertThrows(EmptyFieldException.class, () -> service.update(review1, null))
    );
    verify(repository, times(0)).findById(anyString());
    verify(repository, times(0)).save(any(Reviews.class));
    verify(imagesService, times(0)).update(anyString(), any(MultipartFile.class));
  }

  @Test
  void update_throwsDocumentNotFoundException() throws IOException {
    Reviews test = review1;
    test.setId("99");
    when(repository.findById("99")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.update(test, mock(MultipartFile.class)));
    verify(repository).findById("99");
    verify(repository, times(0)).save(test);
    verify(imagesService, times(0)).update(anyString(), any(MultipartFile.class));
  }

  @Test
  void delete_success() {
    String expected = "Operación realizada con éxito";
    when(repository.findById("1")).thenReturn(Optional.of(review1));

    String actual = service.delete("1");

    assertEquals(expected, actual);
    verify(repository).findById("1");
    verify(repository).deleteById("1");
  }

  @Test
  void delete_throwsEmptyFieldException() {
    assertAll(
      () -> assertThrows(EmptyFieldException.class, () -> service.delete(null)),
      () -> assertThrows(EmptyFieldException.class, () -> service.delete(""))
    );
    verify(repository, times(0)).findById(anyString());
    verify(repository, times(0)).deleteById(anyString());
  }

  @Test
  void delete_throwsDocumentNotFoundException() {
    when(repository.findById("99")).thenReturn(Optional.empty());

    assertThrows(DocumentNotFoundException.class, () -> service.delete("99"));
    verify(repository).findById("99");
    verify(repository, times(0)).deleteById("99");
  }
}
