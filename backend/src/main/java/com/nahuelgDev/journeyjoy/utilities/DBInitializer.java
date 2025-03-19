package com.nahuelgDev.journeyjoy.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nahuelgDev.journeyjoy.collections.Admin;
import com.nahuelgDev.journeyjoy.collections.Images;
import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.repositories.*;
import com.nahuelgDev.journeyjoy.utilities.constants.InitData;

import jakarta.annotation.PostConstruct;

@Component
public class DBInitializer {
  private final TravelsRepository travelsRepo;
  private final StayPlacesRepository stayPlacesRepo;
  private final ReviewsRepository reviewsRepo;
  private final AdminRepository adminRepo;
  private final ImagesRepository imagesRepository;


  public DBInitializer(TravelsRepository travelsRepo, StayPlacesRepository stayPlacesRepo,
    ReviewsRepository reviewsRepo, AdminRepository adminRepo, ImagesRepository imagesRepository
  ) {
    this.travelsRepo = travelsRepo;
    this.stayPlacesRepo = stayPlacesRepo;
    this.reviewsRepo = reviewsRepo;
    this.adminRepo = adminRepo;
    this.imagesRepository = imagesRepository;
  }

  @PostConstruct
  public void init() throws Exception {
    // TODO: move the delete callbacks to the resetDatabaseMethod in the future
    adminRepo.deleteAll();
    imagesRepository.deleteAll();
    stayPlacesRepo.deleteAll();
    travelsRepo.deleteAll();
    
    Admin admin = new Admin();
    admin.setUsername("admin");
    admin.setPassword(new BCryptPasswordEncoder().encode("abc123"));
    adminRepo.save(admin);

    for (int i = 0; i < InitData.images().indexList.size(); i++) {
      String imageId = InitData.images().indexList.get(i);
      Images imageData = (Images) InitData.images().data.get(i);

      if (!imagesRepository.findById(imageId).isPresent()) 
        imagesRepository.save(imageData);
    }

    for (int i = 0; i < InitData.stayPlaces().indexList.size(); i++) {
      String placeId = InitData.stayPlaces().indexList.get(i);
      StayPlaces placeData = (StayPlaces) InitData.stayPlaces().data.get(i);

      if (!stayPlacesRepo.findById(placeId).isPresent())
        stayPlacesRepo.save(placeData);
    }

    for (int i = 0; i < InitData.travels().indexList.size(); i++) {
      String travelName = InitData.travels().indexList.get(i);
      Travels travelData = (Travels) InitData.travels().data.get(i);

      if (!travelsRepo.findByName(travelName).isPresent()) 
        travelsRepo.save(travelData);
    }
  }
}
