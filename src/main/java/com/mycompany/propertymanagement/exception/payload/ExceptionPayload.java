package com.mycompany.propertymanagement.exception.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionPayload {

	private String message;
	private String dateTime;
	private HttpStatus httpStatus;

}
