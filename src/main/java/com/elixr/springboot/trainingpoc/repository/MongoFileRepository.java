package com.elixr.springboot.trainingpoc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.elixr.springboot.trainingpoc.model.MongoFile;


@Repository
public interface MongoFileRepository extends MongoRepository<MongoFile, UUID>{
    List findByUserName(String userName);
}
