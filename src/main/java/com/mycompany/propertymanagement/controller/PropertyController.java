package com.mycompany.propertymanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.exception.BusinessException;
import com.mycompany.propertymanagement.exception.GenericRestException;
import com.mycompany.propertymanagement.service.PropertyService;

@RestController
@RequestMapping("/api/v1")
public class PropertyController {

	private final PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	// RESTFUL API is just mapping of a url to a java class function
	// http://localhost:8080/api/v1/properties/hello
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello ";
	}

	@PostMapping("/properties")
	public ResponseEntity<PropertyDTO> saveProperty(@RequestBody PropertyDTO propertyDTO) {
		ResponseEntity<PropertyDTO> responseEntity;
		try {
			propertyDTO = propertyService.saveProperty(propertyDTO);
			responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);

		} catch (Exception e) {
			throw new GenericRestException("not able to save now! please try later");
		}
		return responseEntity;
	}

	@GetMapping("/properties")
	public ResponseEntity<List<PropertyDTO>> getAllProperties() {
		ResponseEntity<List<PropertyDTO>> responseEntity;
		try {
			List<PropertyDTO> propertyList = propertyService.getAllProperties();
			responseEntity = new ResponseEntity<>(propertyList, HttpStatus.OK);
		} catch (Exception e) {
			throw new GenericRestException("error please try later!");
		}
		return responseEntity;
	}

	@PutMapping("/properties/{propertyId}")
	public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO,
			@PathVariable Long propertyId) {
		ResponseEntity<PropertyDTO> responseEntity;
		try {
			propertyDTO = propertyService.updateProperty(propertyDTO, propertyId);
			responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new GenericRestException("not able to update now try again");
		}
		return responseEntity;
	}

	@PatchMapping("/properties/update-description/{propertyId}")
	public ResponseEntity<PropertyDTO> updatePropertyDescription(@RequestBody PropertyDTO propertyDTO,
			@PathVariable Long propertyId) {
		ResponseEntity<PropertyDTO> responseEntity;
		try {
			propertyDTO = propertyService.updatePropertyDescription(propertyDTO, propertyId);
			responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.OK);
		} catch (BusinessException e) {
			throw new GenericRestException("not able to update now try again");
		}
		return responseEntity;
	}

	@PatchMapping("/properties/update-price/{propertyId}")
	public ResponseEntity<PropertyDTO> updatePropertyPrice(@RequestBody PropertyDTO propertyDTO,
			@PathVariable Long propertyId) {
		ResponseEntity<PropertyDTO> responseEntity;
		try {
			propertyDTO = propertyService.updatePropertyPrice(propertyDTO, propertyId);
			responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new GenericRestException("not able to update now try again");
		}
		return responseEntity;
	}

	@DeleteMapping("/properties/{propertyId}")
	public ResponseEntity deleteProperty(@PathVariable Long propertyId) {
		ResponseEntity<Void> responseEntity;
		try {
			propertyService.deleteProperty(propertyId);
			responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new GenericRestException("id not present in db " + propertyId);
		}
		return responseEntity;
	}
}
