package com.nahuelgDev.journeyjoy.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@Document
public class Images {
// DEV NOTE: Es mejor práctica almacenar imágenes y archivos en general en servicios de almacenamiento externos o almacenamiento local
// y no en la base de datos, no importa si es SQL o NOSQL. Esto por cuestiones de eficiencia en consultas, rendimiento, y tamaño de la BdD.
    @Id
    private String id;
    private String name;
    private String contentType;
    private byte[] data;
}
