package it.gov.pagopa.reportingorgsenrollment.mapper;

import javax.validation.Valid;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;

public class ConvertOrganizationEntityToOrganizationModelResponse implements Converter<OrganizationEntity, OrganizationModelResponse> {

    @Override
    public OrganizationModelResponse convert(MappingContext<OrganizationEntity, OrganizationModelResponse> mappingContext) {
        @Valid OrganizationEntity oe = mappingContext.getSource();
        return OrganizationModelResponse.builder()
                .organizationFiscalCode(oe.getRowKey())
                .organizationOnboardingDate(oe.getOrganizationOnboardingDate())
                .build();
    }
}
