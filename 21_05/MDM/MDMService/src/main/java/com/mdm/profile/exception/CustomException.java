package com.mdm.profile.exception;

import com.mdm.profile.io.StatusMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class used to throw custom exception for bad request and invalid response.
 * @author Mukesh
 *
 */

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String status;
	private StatusMessage statusMessage;
	
}
