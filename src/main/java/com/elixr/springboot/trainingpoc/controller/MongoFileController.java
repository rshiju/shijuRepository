package com.elixr.springboot.trainingpoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elixr.springboot.trainingpoc.dto.Response;
import com.elixr.springboot.trainingpoc.dto.SuccessResponse1;
import com.elixr.springboot.trainingpoc.dto.SuccessResponse2;
import com.elixr.springboot.trainingpoc.model.MongoFile;
import com.elixr.springboot.trainingpoc.model.MongoFileDto;
import com.elixr.springboot.trainingpoc.model.MongoFileDto2;
import com.elixr.springboot.trainingpoc.service.MongoFileService;

/*
 * REST API end point
 * @author Shiju.Raveendran
 */
@RestController
public class MongoFileController {

	@Value("${file.upload.dir}")
	private String FILE_UPLOAD_DIR;
	
	@Autowired
	MongoFileService mongoFileService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("userName") String userName) {
		MongoFile mongoFile = mongoFileService.uploadFile(multipartFile, userName);
		Response response = new Response(true, mongoFile.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/file/{id}")
	public ResponseEntity<?> getFileById(@PathVariable("id") String id) {
		MongoFileDto mongoFileDto = mongoFileService.getFileById(id);
		SuccessResponse1 response = new SuccessResponse1(true, mongoFileDto.getUserName(), mongoFileDto.getUploadTime(),
				mongoFileDto.getFileName(), mongoFileDto.getContent());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/file/user/{userName}")
	public ResponseEntity<?> getFilesByUserName(@PathVariable("userName") String userName) {
		List<MongoFileDto2> mongoFilesListFromDB = mongoFileService.getFilesByUserName(userName);
		SuccessResponse2 response = new SuccessResponse2("success", userName, mongoFilesListFromDB);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
