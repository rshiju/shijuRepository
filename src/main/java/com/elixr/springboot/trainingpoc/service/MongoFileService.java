package com.elixr.springboot.trainingpoc.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.elixr.springboot.trainingpoc.model.MongoFile;
import com.elixr.springboot.trainingpoc.model.MongoFileDto;
import com.elixr.springboot.trainingpoc.model.MongoFileDto2;

public interface MongoFileService {

	MongoFile uploadFile(MultipartFile file, String userName);

	MongoFileDto getFileById(String id);

	List<MongoFileDto2> getFilesByUserName(String userName);
}
