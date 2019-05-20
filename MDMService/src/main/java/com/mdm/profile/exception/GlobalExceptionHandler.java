package com.mdm.profile.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mdm.profile.io.StatusMessage;
import com.mdm.profile.utils.MessageCodes;

import lombok.extern.slf4j.Slf4j;


/**
 * This class used to define global and custom exceptions for server internal error,
 *  custom, bad request etc,.
 * @author Mukesh
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * This method used to throw server internal error at global level
	 * @author Mukesh
	 * @param model
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponseModel> generalException(Exception e){
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus(MessageCodes.INTERNAL_SERVER_ERROR);
		model.setStatusMessage(new StatusMessage(MessageCodes.INTERNAL_SERVER_ERROR_MSG,e.getMessage()));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * This method used to send bad request response error at global level
	 * @author Mukesh
	 * @param e
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponseModel> handleIllegalArgumentException(IllegalArgumentException e)  {
		log.error(e.getMessage());
		ExceptionResponseModel model = new ExceptionResponseModel();
		model.setStatus(MessageCodes.BAD_REQUEST);
		model.setStatusMessage(new StatusMessage(MessageCodes.BAD_REQUEST_MSG, e.getMessage()));
		return new ResponseEntity<ExceptionResponseModel>(model, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
}
