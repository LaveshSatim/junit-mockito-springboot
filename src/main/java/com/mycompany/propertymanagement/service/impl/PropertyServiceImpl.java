package com.mycompany.propertymanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.propertymanagement.converter.PropertyConverter;
import com.mycompany.propertymanagement.dto.PropertyDTO;
import com.mycompany.propertymanagement.entity.PropertyEntity;
import com.mycompany.propertymanagement.exception.BusinessException;
import com.mycompany.propertymanagement.repository.PropertyRepository;
import com.mycompany.propertymanagement.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;
	private final PropertyConverter propertyConverter;

	@Override
	public PropertyDTO saveProperty(PropertyDTO propertyDTO) {

		PropertyEntity pe = propertyConverter.convertDTOtoEntity(propertyDTO);
		pe = propertyRepository.save(pe);

		propertyDTO = propertyConverter.convertEntityToDTO(pe);
		return propertyDTO;
	}

	public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyConverter propertyConverter) {
		this.propertyRepository = propertyRepository;
		this.propertyConverter = propertyConverter;
	}

	@Override
	public List<PropertyDTO> getAllProperties() {

		List<PropertyEntity> listOfProps = (List<PropertyEntity>) propertyRepository.findAll();
		List<PropertyDTO> propList = new ArrayList<>();

		for (PropertyEntity pe : listOfProps) {
			PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
			propList.add(dto);
		}
		return propList;

	}

	@Override
	public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {

		Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
		PropertyDTO dto = null;
		if (optEn.isPresent()) {

			PropertyEntity pe = optEn.get();// data from database
			pe.setTitle(propertyDTO.getTitle());
			pe.setAddress(propertyDTO.getAddress());
			pe.setOwnerEmail(propertyDTO.getOwnerEmail());
			pe.setOwnerName(propertyDTO.getOwnerName());
			pe.setPrice(propertyDTO.getPrice());
			pe.setDescription(propertyDTO.getDescription());
			dto = propertyConverter.convertEntityToDTO(pe);
			propertyRepository.save(pe);
		}
		return dto;
	}

	@Override
	public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) throws BusinessException {
		Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
		PropertyDTO dto = null;
		if (optEn.isPresent()) {
			PropertyEntity pe = optEn.get();// data from database
			pe.setDescription(propertyDTO.getDescription());
			dto = propertyConverter.convertEntityToDTO(pe);
			propertyRepository.save(pe);
		} else {
			throw new BusinessException("No Property Found for Update");
		}
		return dto;
	}

	@Override
	public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
		Optional<PropertyEntity> optEn = propertyRepository.findById(propertyId);
		PropertyDTO dto = null;
		if (optEn.isPresent()) {
			PropertyEntity pe = optEn.get();// data from database
			pe.setPrice(propertyDTO.getPrice());
			dto = propertyConverter.convertEntityToDTO(pe);
			propertyRepository.save(pe);
		}
		return dto;
	}

	@Override
	public void deleteProperty(Long propertyId) {
		propertyRepository.deleteById(propertyId);
	}
}
