package com.nahuelgDev.journeyjoy.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Images {
    @Id
    private String id;
    private String name;
    private String contentType;
    private byte[] data;
}
