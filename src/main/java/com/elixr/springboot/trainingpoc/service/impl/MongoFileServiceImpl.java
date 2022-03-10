package com.elixr.springboot.trainingpoc.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.elixr.springboot.trainingpoc.advice.TrainingPocException;
import com.elixr.springboot.trainingpoc.model.MongoFile;
import com.elixr.springboot.trainingpoc.model.MongoFileDto;
import com.elixr.springboot.trainingpoc.model.MongoFileDto2;
import com.elixr.springboot.trainingpoc.repository.MongoFileRepository;
import com.elixr.springboot.trainingpoc.service.MongoFileService;

@Service
public class MongoFileServiceImpl implements MongoFileService {

	@Value("${file.upload.dir}")
	private String FILE_UPLOAD_DIR;

	@Autowired
	MongoFileRepository mongoFileRepository;

	public MongoFile uploadFile(MultipartFile multipartFile, String userName) {
		validatePreRequisitesForUpload(userName, multipartFile);
		String fileName = multipartFile.getOriginalFilename();
		UUID id = UUID.randomUUID();
		MongoFile mongoFile = new MongoFile();
		mongoFile.setUserName(userName);
		mongoFile.setId(id);
		mongoFile.setFileName(fileName);
		mongoFile.setUploadTime(new Date());
		mongoFileRepository.save(mongoFile);
		saveFileInDisk(multipartFile, mongoFile);
		return mongoFile;
	}

	@Override
	public MongoFileDto getFileById(String id) {
		validatePreRequisitesForGetFileById(id, null);
		Optional<MongoFile> mongoFileFromDB = mongoFileRepository.findById(getValidUUID(id));
		if (!mongoFileFromDB.isPresent()) {
			throw new TrainingPocException(TrainingPocException.FILE_NOT_FOUND);
		}
		return convertMongFileToDTO(mongoFileFromDB.get());
	}

	@Override
	public List<MongoFileDto2> getFilesByUserName(String userName) {
		validatePreRequisitesForGetFileByUser(userName);
		List<MongoFile> mongoFilesListFromDB = mongoFileRepository.findByUserName(userName);
		return convertMongFileToDTO2(mongoFilesListFromDB);
	}

	private String getFileContent(String fileName, UUID id) {
		Path path = Paths.get(FILE_UPLOAD_DIR + "/" + fileName);
		if (Files.notExists(path)) {
			throw new TrainingPocException(TrainingPocException.FILE_NOT_FOUND_ON_STORAGE_DEVICE);
		}
		BufferedReader reader;
		String data = "";
		try {
			reader = Files.newBufferedReader(path);
			data = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TrainingPocException(TrainingPocException.FILE_NOT_FOUND_ON_STORAGE_DEVICE);
		}
		return data;
	}

	private MongoFileDto convertMongFileToDTO(MongoFile mongoFile) {
		String fileContent = getFileContent(mongoFile.getFileName(), mongoFile.getId());
		return new MongoFileDto(mongoFile.getId(), mongoFile.getFileName(), mongoFile.getUserName(),
				mongoFile.getUploadTime(), fileContent);
	}

	private List<MongoFileDto2> convertMongFileToDTO2(List<MongoFile> mongoFilesListFromDB) {
		List<MongoFileDto2> mongoFilesList = new ArrayList<MongoFileDto2>();
		for (MongoFile mongoFile : mongoFilesListFromDB) {
			MongoFileDto2 mongoFileDto = new MongoFileDto2(mongoFile.getFileName(), mongoFile.getUploadTime(),
					mongoFile.getId());
			mongoFilesList.add(mongoFileDto);
		}
		return mongoFilesList;
	}

	private void saveFileInDisk(MultipartFile multipartFile, MongoFile mongoFile) {
		try {
			File myFile = new File(FILE_UPLOAD_DIR + "/" + mongoFile.getFileName());
			myFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(myFile);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TrainingPocException(TrainingPocException.SAVING_FILE_FAILED);

		}
	}

	private void validatePreRequisitesForUpload(String userName, MultipartFile file) {
		validateUserName(userName);
		if (!"text/plain".equals(file.getContentType())) {
			throw new TrainingPocException(TrainingPocException.INVALID_FILE_TYPE);
		}
	}

	private void validatePreRequisitesForGetFileById(String id, MultipartFile file) {
		UUID uuid = null;
		if (id == null || id.trim().length() == 0) {
			throw new TrainingPocException(TrainingPocException.MISSING_ID);
		} else {
			uuid = getValidUUID(id);
		}
	}

	private void validatePreRequisitesForGetFileByUser(String userName) {
		validateUserName(userName);
	}

	private void validateUserName(String userName) {
		if (!StringUtils.hasText(userName)) {
			throw new TrainingPocException(TrainingPocException.MISSING_USERNAME);
		}
	}

	private UUID getValidUUID(String id) {
		try {
			return UUID.fromString(id);
		} catch (Exception ex) {
			throw new TrainingPocException(TrainingPocException.INVALID_ID);
		}
	}
}
