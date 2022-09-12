package it.gov.pagopa.reportingorgsenrollment.controller.impl;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.gov.pagopa.reportingorgsenrollment.controller.IEnrollmentsController;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.service.EnrollmentsService;

@RestController
public class EnrollmentsController implements IEnrollmentsController {
	
	@Autowired 
	private EnrollmentsService enrollmentsService;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public ResponseEntity<OrganizationModelResponse> createOrganization(@NotBlank String organizationFiscalCode) {
		return new ResponseEntity<>(
                modelMapper.map(enrollmentsService.createOrganization(organizationFiscalCode), OrganizationModelResponse.class),
                HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> removeOrganization(@NotBlank String organizationFiscalCode) {
		enrollmentsService.deleteOrganization(organizationFiscalCode);
        return new ResponseEntity<>(
                "\"The enrollment to reporting service for the organization "+organizationFiscalCode+" was successfully removed\"",
                HttpStatus.OK);
	}

	@Override
	public ResponseEntity<OrganizationModelResponse> getOrganization(@NotBlank String organizationFiscalCode) {
		return new ResponseEntity<>(
                modelMapper.map(enrollmentsService.getOrganization(organizationFiscalCode), OrganizationModelResponse.class),
                HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrganizationModelResponse>> getOrganizations() {
		return new ResponseEntity<>(
                this.mapList(enrollmentsService.getOrganizations(), OrganizationModelResponse.class),
                HttpStatus.OK);
	}
	
	
	private <S, T> List<T> mapList(Spliterator<S> source, Class<T> targetClass) {
		return StreamSupport.stream(source,false) 
		  .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}
}
