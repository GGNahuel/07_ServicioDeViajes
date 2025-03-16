package com.nahuelgDev.journeyjoy.utilities.constants;

import java.util.List;

import lombok.AllArgsConstructor;

public class InitData {
  @AllArgsConstructor
  public static class ReturnType {
    public List<String> indexList;
    public List<String> data;
  }

  public static ReturnType travels() {
    List<String> indexList = List.of("Ruta Patagónica", "Circuito Andino", "Caribe Mexicano", "Ruta Europea", "Ruta por los Balcanes");
    List<String> data = List.of(
      """
        {
          "name": "Ruta Patagónica",
          "longInDays": 10,
          "maxCapacity": 40,
          "currentCapacity": 0,
          "isAvailable": true,
          "availableDates": ["2025-02-15", "2025-03-01"],
          "destinies": [
            {
              "place": "Bariloche",
              "leaveDay": 1,
              "returnDay": 3,
              "transport": "BUS",
              "stayPlaceId": {
                "id": "101"
              }
            },
            {
              "place": "El Calafate",
              "leaveDay": 4,
              "returnDay": 6,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "102"
              }
            },
            {
              "place": "Ushuaia",
              "leaveDay": 7,
              "returnDay": 10,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "103"
              }
            }
          ],
          "payPlans": [
            { "price": 3500, "planFor": "family" },
            { "price": 1200, "planFor": "individual" }
          ]
        }
      """,

      """
        {
          "name": "Circuito Andino",
          "longInDays": 7,
          "maxCapacity": 30,
          "currentCapacity": 0,
          "isAvailable": true,
          "availableDates": ["2025-04-20", "2025-05-10"],
          "destinies": [
            {
              "place": "Salta",
              "leaveDay": 1,
              "returnDay": 2,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "201"
              }
            },
            {
              "place": "Tilcara",
              "leaveDay": 3,
              "returnDay": 4,
              "transport": "BUS",
              "stayPlaceId": {
                "id": "202"
              }
            },
            {
              "place": "Jujuy",
              "leaveDay": 5,
              "returnDay": 7,
              "transport": "TRAIN",
              "stayPlaceId": {
                "id": "203"
              }
            }
          ],
          "payPlans": [
            { "price": 2000, "planFor": "couple" },
            { "price": 1800, "planFor": "friends" }
          ]
        }
      """,

      """
        {
          "name": "Caribe Mexicano",
          "longInDays": 8,
          "maxCapacity": 50,
          "currentCapacity": 0,
          "isAvailable": true,
          "availableDates": ["2025-06-05", "2025-06-20"],
          "destinies": [
            {
              "place": "Cancún",
              "leaveDay": 1,
              "returnDay": 3,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "301"
              }
            },
            {
              "place": "Playa del Carmen",
              "leaveDay": 4,
              "returnDay": 5,
              "transport": "BUS",
              "stayPlaceId": {
                "id": "302"
              }
            },
            {
              "place": "Cozumel",
              "leaveDay": 6,
              "returnDay": 8,
              "transport": "BOAT",
              "stayPlaceId": {
                "id": "303"
              }
            }
          ],
          "payPlans": [
            { "price": 4000, "planFor": "family" },
            { "price": 3000, "planFor": "friends" }
          ]
        }
      """,

      """
        {
          "name": "Ruta Europea",
          "longInDays": 15,
          "maxCapacity": 20,
          "currentCapacity": 0,
          "isAvailable": true,
          "availableDates": ["2025-09-10"],
          "destinies": [
            {
              "place": "París, Francia",
              "leaveDay": 1,
              "returnDay": 4,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "401"
              }
            },
            {
              "place": "Roma, Italia",
              "leaveDay": 5,
              "returnDay": 9,
              "transport": "TRAIN",
              "stayPlaceId": {
                "id": "402"
              }
            },
            {
              "place": "Madrid, España",
              "leaveDay": 10,
              "returnDay": 15,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "403"
              }
            }
          ],
          "payPlans": [
            { "price": 6000, "planFor": "couple" },
            { "price": 3500, "planFor": "individual" }
          ]
        }
      """,

      """
        {
          "name": "Ruta por los Balcanes",
          "longInDays": 12,
          "maxCapacity": 30,
          "currentCapacity": 0,
          "isAvailable": true,
          "availableDates": ["2025-10-05", "2025-11-01"],
          "destinies": [
            {
              "place": "Dubrovnik, Croacia",
              "leaveDay": 1,
              "returnDay": 3,
              "transport": "PLANE",
              "stayPlaceId": {
                "id": "601"
              }
            },
            {
              "place": "Mostar, Bosnia y Herzegovina",
              "leaveDay": 4,
              "returnDay": 6,
              "transport": "BUS",
              "stayPlaceId": {
                "id": "602"
              }
            },
            {
              "place": "Kotor, Montenegro",
              "leaveDay": 7,
              "returnDay": 9,
              "transport": "BOAT",
              "stayPlaceId": {
                "id": "603"
              }
            },
            {
              "place": "Belgrado, Serbia",
              "leaveDay": 10,
              "returnDay": 12,
              "transport": "TRAIN",
              "stayPlaceId": {
                "id": "604"
              }
            }
          ],
          "payPlans": [
            { "price": 3200, "planFor": "couple" },
            { "price": 2800, "planFor": "friends" }
          ]
        }
      """
    );

    return new ReturnType(indexList, data);
  }

  public static ReturnType stayPlaces() {
    List<String> indexes = List.of(
      "101", "102", "103",
      "201", "202", "203",
      "301", "302", "303",
      "401", "402", "403",
      "601", "602", "603", "604"
    );
    List<String> data = List.of(
      """
        {
          "id": "101",
          "from": "Bariloche",
          "name": "Hotel Los Andes",
          "description": "Hotel 4 estrellas con vista al lago Nahuel Huapi.",
          "rating": 4.7
        }
      """,

      """
        {
          "id": "102",
          "from": "El Calafate",
          "name": "Hostel Glaciar",
          "description": "Albergue económico cercano al glaciar Perito Moreno.",
          "rating": 4.3
        }
      """,
          
      """
        {
          "id": "103",
          "from": "Ushuaia",
          "name": "Cabañas del Fin del Mundo",
          "description": "Cabañas rústicas con vistas al canal Beagle.",
          "rating": 4.8
        }
      """,

      """
        {
          "id": "201",
          "from": "Salta",
          "name": "Hotel Colonial Salta",
          "description": "Hotel boutique en el centro histórico.",
          "rating": 4.5
        }
      """,

      """
        {
          "id": "202",
          "from": "Tilcara",
          "name": "Hostería Quebrada",
          "description": "Pequeño hotel en la quebrada de Humahuaca.",
          "rating": 4.6
        }
      """,

      """
        {
          "id": "203",
          "from": "Jujuy",
          "name": "Eco Lodge Puna",
          "description": "Alojamiento sostenible con vistas panorámicas.",
          "rating": 4.4
        }
      """,

      """
        {
          "id": "301",
          "from": "Cancún",
          "name": "Resort Playa Azul",
          "description": "Resort todo incluido frente a la playa.",
          "rating": 4.9
        }
      """,

      """
        {
          "id": "302",
          "from": "Playa del Carmen",
          "name": "Hotel Riviera Maya",
          "description": "Elegante hotel boutique cercano a la 5ta Avenida.",
          "rating": 4.7
        }
      """,

      """
        {
          "id": "303",
          "from": "Cozumel",
          "name": "Villa Cozumel",
          "description": "Villas privadas en la isla.",
          "rating": 4.8
        }
      """,

      """
        {
          "id": "401",
          "from": "París",
          "name": "Hotel Eiffel",
          "description": "Hotel cercano a la torre Eiffel.",
          "rating": 4.7
        }
      """,

      """
        {
          "id": "402",
          "from": "Roma",
          "name": "Hostal Coliseo",
          "description": "Hostal económico con desayuno incluido.",
          "rating": 4.3
        }
      """,

      """
        {
          "id": "403",
          "from": "Madrid",
          "name": "Hotel Gran Vía",
          "description": "Hotel céntrico con vistas a la Gran Vía.",
          "rating": 4.6
        }
      """,

      """
        {
          "id": "601",
          "from": "Dubrovnik",
          "name": "Hotel Mura",
          "description": "Hotel con vista al casco antiguo de Dubrovnik.",
          "rating": 4.7
        }
      """,

      """
        {
          "id": "602",
          "from": "Mostar",
          "name": "Hostal Stari Most",
          "description": "Hostal cercano al emblemático puente de Mostar.",
          "rating": 4.5
        }
      """,

      """
        {
          "id": "603",
          "from": "Kotor",
          "name": "Villa Adriática",
          "description": "Villa con vistas espectaculares a la bahía de Kotor.",
          "rating": 4.8
        }
      """,

      """
        {
          "id": "604",
          "from": "Belgrado",
          "name": "Hotel Danubio",
          "description": "Hotel moderno a orillas del río Danubio.",
          "rating": 4.6
        }
      """
    );

    return new ReturnType(indexes, data);
  }
}