package com.elixr.springboot.trainingpoc.dto;

import java.util.UUID;

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
public class Response {
	
	protected boolean success;
	protected UUID id;
}
