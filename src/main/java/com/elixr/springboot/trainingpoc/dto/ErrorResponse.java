package com.elixr.springboot.trainingpoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO to hold API success info message 
 * @author Shiju.Raveendran 
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
	
	protected boolean success;
	protected String message;
}
