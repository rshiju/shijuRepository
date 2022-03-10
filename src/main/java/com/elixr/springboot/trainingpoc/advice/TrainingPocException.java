package com.elixr.springboot.trainingpoc.advice;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Component
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class TrainingPocException extends RuntimeException {

	public static final String MISSING_USERNAME = "MISSING_USERNAME";
	public static final String MISSING_ID = "MISSING_ID";
	public static final String INVALID_ID = "MISSING_ID";
	public static final String INVALID_FILE_TYPE = "INVALID_FILE_TYPE";
	public static final String SAVING_FILE_FAILED = "SAVING_FILE_FAILED";
	public static final String FILE_NOT_FOUND = "FILE_NOT_FOUND";
	public static final String FILE_NOT_FOUND_ON_STORAGE_DEVICE = "FILE_NOT_FOUND_ON_STORAGE_DEVICE";
	public static final String ERROR_READING_FILE_FROM_STORAGE = "ERROR_READING_FILE_FROM_STORAGE";

	@NonNull
	private String errorCode;

	private static HashMap<String, String> errorCodeToMsgMap = new HashMap<String, String>();
	private static HashMap<String, HttpStatus> errorCodeToHttpStatusCodeMap = new HashMap<String, HttpStatus>();

	public String getErrorMessage(String errorCode) {
		if (errorCodeToMsgMap.isEmpty()) {
			populatErrorCodeToMsgMap();
		}
		return errorCodeToMsgMap.get(errorCode);
	}

	public HttpStatus getHttpStatusCode(String errorCode) {
		if (errorCodeToHttpStatusCodeMap.isEmpty()) {
			populatErrorCodeToHttpStatusCodeMap();
		}
		return errorCodeToHttpStatusCodeMap.get(errorCode);
	}
	
	private void populatErrorCodeToMsgMap() {
		errorCodeToMsgMap.put(INVALID_FILE_TYPE, "Invalid file type");
		errorCodeToMsgMap.put(MISSING_ID, "Missing id value in rquest. Id is mandatory");
		errorCodeToMsgMap.put(INVALID_ID, "Invalid id value in rquest. Id should be of type UUID");
		errorCodeToMsgMap.put(INVALID_FILE_TYPE, "Invalid file type. Only .txt files are accepted");
		errorCodeToMsgMap.put(SAVING_FILE_FAILED, "Unable to save file to cloud storage device");
		errorCodeToMsgMap.put(FILE_NOT_FOUND, "File not found for the given id");
		errorCodeToMsgMap.put(FILE_NOT_FOUND_ON_STORAGE_DEVICE, "File not found in sotorage device for the given id");
		errorCodeToMsgMap.put(ERROR_READING_FILE_FROM_STORAGE, "Error reading file from storage device");
	}
	private void populatErrorCodeToHttpStatusCodeMap() {
		errorCodeToHttpStatusCodeMap.put(INVALID_FILE_TYPE, HttpStatus.BAD_REQUEST);
		errorCodeToHttpStatusCodeMap.put(MISSING_ID, HttpStatus.NOT_FOUND);
		errorCodeToHttpStatusCodeMap.put(INVALID_ID, HttpStatus.BAD_REQUEST);
		errorCodeToHttpStatusCodeMap.put(INVALID_FILE_TYPE, HttpStatus.BAD_REQUEST);
		errorCodeToHttpStatusCodeMap.put(SAVING_FILE_FAILED, HttpStatus.BAD_REQUEST);
		errorCodeToHttpStatusCodeMap.put(FILE_NOT_FOUND, HttpStatus.BAD_REQUEST);
		errorCodeToHttpStatusCodeMap.put(FILE_NOT_FOUND_ON_STORAGE_DEVICE, HttpStatus.BAD_REQUEST);
		errorCodeToHttpStatusCodeMap.put(ERROR_READING_FILE_FROM_STORAGE, HttpStatus.BAD_REQUEST);
	}
	}
