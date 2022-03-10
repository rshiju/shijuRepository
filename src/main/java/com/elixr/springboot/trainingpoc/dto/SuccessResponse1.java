package com.elixr.springboot.trainingpoc.dto;

import java.util.Date;

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
public class SuccessResponse1{
	private boolean success;
	private String username;
    private Date uploadTime;
    private String filename;
    private String content;
}
