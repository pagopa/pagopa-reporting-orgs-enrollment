package it.gov.pagopa.reportingorgsenrollment.controller.impl;

import java.util.List;

import javax.validation.constraints.NotBlank;

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
	
	@Override
	public ResponseEntity<OrganizationModelResponse> createOrganization(@NotBlank String organizationFiscalCode) {
		return new ResponseEntity<>(
                enrollmentsService.createOrganization(organizationFiscalCode),
                HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> removeOrganization(@NotBlank String organizationFiscalCode) {
		enrollmentsService.removeOrganization(organizationFiscalCode);
        return new ResponseEntity<>(
                "\"The enrollment to reporting service for the organization "+organizationFiscalCode+" was successfully removed\"",
                HttpStatus.OK);
	}

	@Override
	public ResponseEntity<OrganizationModelResponse> getOrganization(@NotBlank String organizationFiscalCode) {
		return new ResponseEntity<>(
                enrollmentsService.getOrganization(organizationFiscalCode),
                HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<OrganizationModelResponse>> getOrganizations() {
		return new ResponseEntity<>(
                enrollmentsService.getOrganizations(),
                HttpStatus.OK);
	}
	
	
	
}
