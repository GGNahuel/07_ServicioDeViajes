package com.nahuelgDev.journeyjoy.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nahuelgDev.journeyjoy.collections.Admin;
import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.repositories.AdminRepository;
import com.nahuelgDev.journeyjoy.repositories.ReviewsRepository;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;
import com.nahuelgDev.journeyjoy.utilities.constants.InitData;

import jakarta.annotation.PostConstruct;

@Component
public class DBInitializer {
  private final TravelsRepository travelsRepo;
  private final StayPlacesRepository stayPlacesRepo;
  private final ReviewsRepository reviewsRepo;
  private final AdminRepository adminRepo;

  private final ObjectMapper objectMapper;

  public DBInitializer(TravelsRepository travelsRepo, StayPlacesRepository stayPlacesRepo,
    ReviewsRepository reviewsRepo, AdminRepository adminRepo, ObjectMapper objectMapper
  ) {
    this.travelsRepo = travelsRepo;
    this.stayPlacesRepo = stayPlacesRepo;
    this.reviewsRepo = reviewsRepo;
    this.adminRepo = adminRepo;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void init() throws Exception {
    Admin admin = new Admin();
    admin.setUsername("admin");
    admin.setPassword(new BCryptPasswordEncoder().encode("abc123"));
    adminRepo.deleteAll();
    adminRepo.save(admin);

    for (int i = 0; i < InitData.stayPlaces().indexList.size(); i++) {
      String placeId = InitData.stayPlaces().indexList.get(i);
      String placeData = InitData.stayPlaces().data.get(i);

      if (!stayPlacesRepo.findById(placeId).isPresent()) {
        StayPlaces place = objectMapper.readValue(placeData, StayPlaces.class);

        stayPlacesRepo.save(place);
      }
    }

    for (int i = 0; i < InitData.travels().indexList.size(); i++) {
      String travelName = InitData.travels().indexList.get(i);
      String travelData = InitData.travels().data.get(i);

      if (!travelsRepo.findByName(travelName).isPresent()) {
        Travels travel = objectMapper.readValue(travelData, Travels.class);

        travelsRepo.save(travel);
      }
    }
  }
}
