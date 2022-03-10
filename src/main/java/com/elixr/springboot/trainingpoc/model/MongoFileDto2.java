package com.elixr.springboot.trainingpoc.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoFileDto2 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String filename;
    private Date uploadTime;
    private UUID id;
}
