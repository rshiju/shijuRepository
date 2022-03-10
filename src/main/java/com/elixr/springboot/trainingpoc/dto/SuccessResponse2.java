package com.elixr.springboot.trainingpoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO to hold API response for getFileByUserName
 * @author Shiju.Raveendran 
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuccessResponse2{
	private String success;
	private String username;
	private Object files;
}
