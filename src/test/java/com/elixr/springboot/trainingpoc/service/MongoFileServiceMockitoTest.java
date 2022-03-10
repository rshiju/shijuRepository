package com.elixr.springboot.trainingpoc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.elixr.springboot.trainingpoc.advice.TrainingPocException;
import com.elixr.springboot.trainingpoc.model.MongoFile;
import com.elixr.springboot.trainingpoc.model.MongoFileDto;
import com.elixr.springboot.trainingpoc.model.MongoFileDto2;
import com.elixr.springboot.trainingpoc.repository.MongoFileRepository;
import com.elixr.springboot.trainingpoc.service.MongoFileService;
import com.elixr.springboot.trainingpoc.service.impl.MongoFileServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MongoFileServiceMockitoTest {

	@InjectMocks
	MongoFileServiceImpl mongoFileService;

	@Mock
	MongoFileRepository mongoFileRepository;
	
	MongoFile mongoFile;
	MultipartFile sampleInputFile;
	MongoFileDto mongoFileDto;
	String userName;
	String testFleName = "testfile-mock.txt";
	String testFileContent = "test file content";
	List<MongoFileDto2> mongoFilesDTOList;
	List<MongoFile> mongoFilesList;
	MongoFileDto2 mongoFileDto2;
	String id;

	@BeforeEach
	void setUp() {
		mongoFile = new MongoFile();
		mongoFileDto2 = new MongoFileDto2();
		id = "43db2471-507c-4a73-9d3a-fd7bf01cc261";
		userName = "testUser";
		mongoFileDto = new MongoFileDto(UUID.randomUUID(), testFleName, userName, new Date(), testFileContent);
		mongoFilesDTOList = new ArrayList<MongoFileDto2>();
		mongoFilesDTOList.add(mongoFileDto2);
		mongoFilesList = new ArrayList<MongoFile>();
		mongoFilesList.add(mongoFile);
		sampleInputFile = new MockMultipartFile("File", testFleName, "text/plain",
				"test file content".getBytes());
	}

	@Test
	public void testUploadFile() {
		// when(mongoFileRepository.findById(any())).thenReturn(Optional.ofNullable(mongoFile));
		ReflectionTestUtils.setField(mongoFileService, "FILE_UPLOAD_DIR", "C:/shiju");
		mongoFileService.uploadFile(sampleInputFile, userName);
		verify(mongoFileRepository, times(1)).save(any(MongoFile.class));
	}
	
	@Test
	public void testGetFileById() {
		when(mongoFileRepository.findById(any())).thenReturn(Optional.ofNullable(mongoFile));
		ReflectionTestUtils.setField(mongoFileService, "FILE_UPLOAD_DIR", "C:/shiju");
		try {
			mongoFileService.getFileById(id);
		} catch (TrainingPocException e) {
			assertEquals(e.getErrorCode(), TrainingPocException.FILE_NOT_FOUND_ON_STORAGE_DEVICE);
			// TODO Auto-generated catch block
		}
	}
	
	@Test
	public void testGetFilesByUserName() {
		when(mongoFileRepository.findByUserName(any())).thenReturn(mongoFilesList);
		List<MongoFileDto2>  MongoFileDtoList =  mongoFileService.getFilesByUserName(userName);
		assertEquals(MongoFileDtoList.size(), 1);
	}
}
