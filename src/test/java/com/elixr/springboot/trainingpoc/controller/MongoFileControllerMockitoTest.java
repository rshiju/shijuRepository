package com.elixr.springboot.trainingpoc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.elixr.springboot.trainingpoc.model.MongoFile;
import com.elixr.springboot.trainingpoc.model.MongoFileDto;
import com.elixr.springboot.trainingpoc.model.MongoFileDto2;
import com.elixr.springboot.trainingpoc.service.MongoFileService;

@ExtendWith(MockitoExtension.class)
public class MongoFileControllerMockitoTest {

	@InjectMocks
	MongoFileController mongoFileController;

	@Mock
	MongoFileService mongoFileService;

	MongoFile mongoFile;
	MultipartFile sampleFile;
	MongoFileDto mongoFileDto;
	String userName;
	String testFleName = "testfile-mock.txt";
	String testFileContent = "test file content";
	List<MongoFileDto2> mongoFilesDTOList;
	MongoFileDto2 mongoFileDto2;

	@BeforeEach
	void setUp() {
		mongoFile = new MongoFile();
		mongoFileDto2 = new MongoFileDto2();
		userName = "testUser";
		mongoFileDto = new MongoFileDto(UUID.randomUUID(), testFleName, userName, new Date(), testFileContent);
		mongoFilesDTOList = new ArrayList<>();
		mongoFilesDTOList.add(mongoFileDto2);
	}

	@Test
	public void testUploadFile() {
		when(mongoFileService.uploadFile(any(MultipartFile.class), any(String.class))).thenReturn(mongoFile);
		MockMultipartFile sampleInputFile = new MockMultipartFile("File", testFleName, "text/plain",
				"test file content".getBytes());
		ResponseEntity<?> responseEntity = mongoFileController.uploadFile(sampleInputFile, userName);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testGetFileById() {
		when(mongoFileService.getFileById(any(String.class))).thenReturn(mongoFileDto);
		ResponseEntity<?> responseEntity = mongoFileController.getFileById("testId");
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testGetFileByUserName() {
		when(mongoFileService.getFilesByUserName(any(String.class))).thenReturn(mongoFilesDTOList);
		ResponseEntity<?> responseEntity = mongoFileController.getFileById("testId");
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
