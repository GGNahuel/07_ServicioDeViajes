package com.nahuelgDev.journeyjoy.utilities.constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.nahuelgDev.journeyjoy.collections.Images;
import com.nahuelgDev.journeyjoy.collections.StayPlaces;
import com.nahuelgDev.journeyjoy.collections.Travels;
import com.nahuelgDev.journeyjoy.dataClasses.Destinies;
import com.nahuelgDev.journeyjoy.dataClasses.PayPlans;
import com.nahuelgDev.journeyjoy.enums.PayPlansType;
import com.nahuelgDev.journeyjoy.enums.Transports;

import lombok.AllArgsConstructor;

public class InitData {
  @AllArgsConstructor
  public static class ReturnType {
    public List<String> indexList;
    public List<?> data;
  }

  public static ReturnType travels() {
    List<String> indexList = List.of("Ruta Patagónica", "Circuito Andino", "Caribe Mexicano", "Ruta Europea", "Ruta por los Balcanes");
    Travels travel1 = Travels.builder()
      .name(indexList.get(0))
      .longInDays(10)
      .maxCapacity(40).currentCapacity(0)
      .isAvailable(true)
      .availableDates(List.of(LocalDate.parse("2025-02-15"), LocalDate.parse("2025-03-01")))
      .destinies(List.of(
        Destinies.builder()
          .place("Bariloche")
          .leaveDay(1).returnDay(3)
          .transport(Transports.BUS)
          .stayPlaceId(new StayPlaces("101"))
        .build(),
        Destinies.builder()
          .place("El Calafate")
          .leaveDay(4).returnDay(6)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("102"))
        .build(),
        Destinies.builder()
          .place("Ushuaia")
          .leaveDay(7).returnDay(10)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("103"))
        .build()
      ))
      .payPlans(List.of(
        PayPlans.builder().price(3500.0).planFor(PayPlansType.family).build(),
        PayPlans.builder().price(1200.0).planFor(PayPlansType.individual).build()
      ))
    .build();

    Travels travel2 = Travels.builder()
      .name(indexList.get(1))
      .longInDays(7)
      .maxCapacity(30)
      .currentCapacity(0)
      .isAvailable(true)
      .availableDates(List.of(LocalDate.parse("2025-04-20"), LocalDate.parse("2025-05-10")))
      .destinies(List.of(
        Destinies.builder()
          .place("Salta")
          .leaveDay(1).returnDay(2)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("201"))
        .build(),
        Destinies.builder()
          .place("Tilcara")
          .leaveDay(3).returnDay(4)
          .transport(Transports.BUS)
          .stayPlaceId(new StayPlaces("202"))
        .build(),
        Destinies.builder()
          .place("Jujuy")
          .leaveDay(5).returnDay(7)
          .transport(Transports.TRAIN)
          .stayPlaceId(new StayPlaces("203"))
        .build()
      ))
      .payPlans(List.of(
        PayPlans.builder().price(2000.0).planFor(PayPlansType.couple).build(),
        PayPlans.builder().price(1800.0).planFor(PayPlansType.friends).build()
      ))
    .build();

    Travels travel3 = Travels.builder()
      .name(indexList.get(2))
      .longInDays(8)
      .maxCapacity(50)
      .currentCapacity(0)
      .isAvailable(true)
      .availableDates(List.of(LocalDate.parse("2025-06-05"), LocalDate.parse("2025-06-20")))
      .destinies(List.of(
        Destinies.builder()
          .place("Cancún")
          .leaveDay(1).returnDay(3)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("301"))
        .build(),
        Destinies.builder()
          .place("Playa del Carmen")
          .leaveDay(4).returnDay(5)
          .transport(Transports.BUS)
          .stayPlaceId(new StayPlaces("302"))
        .build(),
        Destinies.builder()
          .place("Cozumel")
          .leaveDay(6).returnDay(8)
          .transport(Transports.BOAT)
          .stayPlaceId(new StayPlaces("303"))
        .build()
      ))
      .payPlans(List.of(
        PayPlans.builder().price(4000.0).planFor(PayPlansType.family).build(),
        PayPlans.builder().price(3000.0).planFor(PayPlansType.friends).build()
      ))
    .build();

    Travels travel4 = Travels.builder()
      .name(indexList.get(3))
      .longInDays(15)
      .maxCapacity(20)
      .currentCapacity(0)
      .isAvailable(true)
      .availableDates(List.of(LocalDate.parse("2025-09-10")))
      .destinies(List.of(
        Destinies.builder()
          .place("París, Francia")
          .leaveDay(1).returnDay(4)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("401"))
        .build(),
        Destinies.builder()
          .place("Roma, Italia")
          .leaveDay(5).returnDay(9)
          .transport(Transports.TRAIN)
          .stayPlaceId(new StayPlaces("402"))
          .build(),
        Destinies.builder()
          .place("Madrid, España")
          .leaveDay(10).returnDay(15)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("403"))
        .build()
      ))
      .payPlans(List.of(
        PayPlans.builder().price(6000.0).planFor(PayPlansType.couple).build(),
        PayPlans.builder().price(3500.0).planFor(PayPlansType.individual).build()
      ))
    .build();

    Travels travel5 = Travels.builder()
      .name(indexList.get(4))
      .longInDays(12)
      .maxCapacity(30)
      .currentCapacity(0)
      .isAvailable(true)
      .availableDates(List.of(LocalDate.parse("2025-10-05"), LocalDate.parse("2025-11-01")))
      .destinies(List.of(
        Destinies.builder()
          .place("Dubrovnik, Croacia")
          .leaveDay(1).returnDay(3)
          .transport(Transports.PLANE)
          .stayPlaceId(new StayPlaces("601"))
        .build(),
        Destinies.builder()
          .place("Mostar, Bosnia y Herzegovina")
          .leaveDay(4).returnDay(6)
          .transport(Transports.BUS)
          .stayPlaceId(new StayPlaces("602"))
        .build(),
        Destinies.builder()
          .place("Kotor, Montenegro")
          .leaveDay(7).returnDay(9)
          .transport(Transports.BOAT)
          .stayPlaceId(new StayPlaces("603"))
        .build(),
        Destinies.builder()
          .place("Belgrado, Serbia")
          .leaveDay(10).returnDay(12)
          .transport(Transports.TRAIN)
          .stayPlaceId(new StayPlaces("604"))
        .build()
      ))
      .payPlans(List.of(
          PayPlans.builder().price(3200.0).planFor(PayPlansType.couple).build(),
          PayPlans.builder().price(2800.0).planFor(PayPlansType.friends).build()
      ))
    .build();

    return new ReturnType(indexList, List.of(travel1, travel2, travel3, travel4, travel5));
  }

  public static ReturnType stayPlaces() {
    List<String> indexes = List.of(
      "101", "102", "103",
      "201", "202", "203",
      "301", "302", "303",
      "401", "402", "403",
      "601", "602", "603", "604"
    );

    StayPlaces stayPlace0 = StayPlaces.builder()
      .id(indexes.get(0))
      .from("Bariloche")
      .name("Hotel los Andes")
      .description("Hotel 4 estrellas con vista al lago Nahuel Huapi")
      .rating(4.7)
    .build();
    StayPlaces stayPlace1 = StayPlaces.builder()
      .id(indexes.get(1))
      .from("El Calafate")
      .name("Hostel Glaciar")
      .description("Albergue económico cercano al glaciar Perito Moreno.")
      .rating(4.3)
    .build();
    StayPlaces stayPlace2 = StayPlaces.builder()
      .id(indexes.get(2))
      .from("Ushuaia")
      .name("Cabañas del Fin del Mundo")
      .description("Cabañas rústicas con vistas al canal Beagle.")
      .rating(4.8)
    .build();
    StayPlaces stayPlace3 = StayPlaces.builder()
      .id(indexes.get(3))
      .from("Salta")
      .name("Hotel Colonial Salta")
      .description("Hotel boutique en el centro histórico.")
      .rating(4.5)
    .build();
    StayPlaces stayPlace4 = StayPlaces.builder()
      .id(indexes.get(4))
      .from("Tilcara")
      .name("Hostería Quebrada")
      .description("Pequeño hotel en la quebrada de Humahuaca.")
      .rating(4.6)
      .build();
    StayPlaces stayPlace5 = StayPlaces.builder()
      .id(indexes.get(5))
      .from("Jujuy")
      .name("Eco Lodge Puna")
      .description("Alojamiento sostenible con vistas panorámicas.")
      .rating(4.4)
    .build();
    StayPlaces stayPlace6 = StayPlaces.builder()
      .id(indexes.get(6))
      .from("Cancún")
      .name("Resort Playa Azul")
      .description("Resort todo incluido frente a la playa.")
      .rating(4.9)
    .build();
    StayPlaces stayPlace7 = StayPlaces.builder()
      .id(indexes.get(7))
      .from("Playa del Carmen")
      .name("Hotel Riviera Maya")
      .description("Elegante hotel boutique cercano a la 5ta Avenida.")
      .rating(4.7)
    .build();
    StayPlaces stayPlace8 = StayPlaces.builder()
      .id(indexes.get(8))
      .from("Cozumel")
      .name("Villa Cozumel")
      .description("Villas privadas en la isla.")
      .rating(4.8)
    .build();
    StayPlaces stayPlace9 = StayPlaces.builder()
      .id(indexes.get(9))
      .from("París")
      .name("Hotel Eiffel")
      .description("Hotel cercano a la torre Eiffel.")
      .rating(4.7)
    .build();
    StayPlaces stayPlace10 = StayPlaces.builder()
      .id(indexes.get(10))
      .from("Roma")
      .name("Hostal Coliseo")
      .description("Hostal económico con desayuno incluido.")
      .rating(4.3)
    .build();
    StayPlaces stayPlace11 = StayPlaces.builder()
      .id(indexes.get(11))
      .from("Madrid")
      .name("Hotel Gran Vía")
      .description("Hotel céntrico con vistas a la Gran Vía.")
      .rating(4.6)
    .build();
    StayPlaces stayPlace12 = StayPlaces.builder()
      .id(indexes.get(12))
      .from("Dubrovnik")
      .name("Hotel Mura")
      .description("Hotel con vista al casco antiguo de Dubrovnik.")
      .rating(4.7)
    .build();
    StayPlaces stayPlace13 = StayPlaces.builder()
      .id(indexes.get(13))
      .from("Mostar")
      .name("Hostal Stari Most")
      .description("Hostal cercano al emblemático puente de Mostar.")
      .rating(4.5)
    .build();
    StayPlaces stayPlace14 = StayPlaces.builder()
      .id(indexes.get(14))
      .from("Kotor")
      .name("Villa Adriática")
      .description("Villa con vistas espectaculares a la bahía de Kotor.")
      .rating(4.8)
    .build();
    StayPlaces stayPlace15 = StayPlaces.builder()
      .id(indexes.get(15))
      .from("Belgrado")
      .name("Hotel Danubio")
      .description("Hotel moderno a orillas del río Danubio.")
      .rating(4.6)
    .build();

    return new ReturnType(indexes, List.of(stayPlace0,stayPlace1,stayPlace2,stayPlace3,stayPlace4,stayPlace5,stayPlace6,stayPlace7,stayPlace8,stayPlace9,stayPlace10,stayPlace11,stayPlace12,stayPlace13,stayPlace14,stayPlace15));
  }

  public static ReturnType images() throws IOException {
    List<String> imagePaths = List.of(
      "src/main/resources/static/travel0_01.webp",
      "src/main/resources/static/travel0_02.webp",
      "src/main/resources/static/travel0_03.webp",

      "src/main/resources/static/travel1_02.webp",
      "src/main/resources/static/travel1_02.webp",
      
      "src/main/resources/static/travel2_01.webp",
      "src/main/resources/static/travel2_02.webp",
      "src/main/resources/static/travel2_03.webp",
      
      "src/main/resources/static/travel3_01.webp",
      "src/main/resources/static/travel3_02.webp",
      "src/main/resources/static/travel3_03.webp",
      
      "src/main/resources/static/travel4_01.webp",
      "src/main/resources/static/travel4_02.webp",
      "src/main/resources/static/travel4_03.webp"
    );

    List<String> idList = new ArrayList<>();
    List<Images> images = new ArrayList<>();

    for (Integer i = 0; i < imagePaths.size(); i++) {
      String id = "travelImg" + i.toString();
      String path = imagePaths.get(i);
      byte[] imageData = Files.readAllBytes(Paths.get(path));

      idList.add(id);
      images.add(
        Images.builder()
          .id(id)
          .name(Paths.get(path).getFileName().toString())
          .contentType("image/webp")
          .data(imageData)
        .build()
      );
    }

    return new ReturnType(idList, images);
  }
}