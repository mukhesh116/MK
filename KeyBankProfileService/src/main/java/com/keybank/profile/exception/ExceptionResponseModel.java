package com.keybank.profile.exception;

import com.keybank.profile.io.StatusMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseModel {

	private String status;
	private StatusMessage statusMessage;
	
}
