/* 
package com.nahuelgDev.journeyjoy.configurations;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dtos.StayPlacesDto;
import com.nahuelgDev.journeyjoy.dtos.TravelsDto;
import com.nahuelgDev.journeyjoy.repositories.StayPlacesRepository;
import com.nahuelgDev.journeyjoy.repositories.TravelsRepository;

@Configuration
public class ModelMapperConfig {
  @Autowired StayPlacesRepository stayPlacesRepo;
  @Autowired TravelsRepository travelsRepo;

  @Bean
  ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();


    return modelMapper;
  }

  private void travelsMapping(ModelMapper modelMapper) {
    if (modelMapper.getTypeMap(Travels.class, TravelsDto.class) != null) {
      return ;
    }

    Converter<StayPlaces, StayPlacesDto> stayPlaceMapping = conv ->
      conv.getSource() != null ?
  }
} */
