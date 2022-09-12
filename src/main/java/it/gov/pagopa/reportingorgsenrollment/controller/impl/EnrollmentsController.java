package it.gov.pagopa.reportingorgsenrollment.controller.impl;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import it.gov.pagopa.reportingorgsenrollment.controller.IEnrollmentsController;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;

@RestController
public class EnrollmentsController implements IEnrollmentsController {

	@Override
	public ResponseEntity<OrganizationModelResponse> createOrganization(@NotBlank String organizationFiscalCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> removeOrganization(@NotBlank String organizationFiscalCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<OrganizationModelResponse> getOrganization(@NotBlank String organizationFiscalCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<OrganizationModelResponse>> getOrganizations() {
		// TODO Auto-generated method stub
		return null;
	}

    

}
