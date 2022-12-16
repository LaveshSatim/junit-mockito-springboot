package com.mycompany.propertymanagement.exception;

import java.time.LocalDateTime;

import org.springframework.data.convert.JodaTimeConverters.LocalDateTimeToDateConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mycompany.propertymanagement.exception.payload.ExceptionPayload;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(GenericRestException.class)
	public ResponseEntity<ExceptionPayload> afterException(GenericRestException exception) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionPayload(exception.getMessage(),
				LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR));

	}
}
