package com.elixr.springboot.trainingpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
/*
 * Entry point for TrainingPOCApplication
 * @author Shiju.Raveendran
 */
@SpringBootApplication
//@ComponentScan
//@EnableMongoRepositories(basePackages = {"com.elixr.springboot.trainingpoc.repository"})
public class TrainingPOCApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingPOCApplication.class, args);
	}

}
