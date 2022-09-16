package it.gov.pagopa.reportingorgsenrollment.config;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.mapper.ConvertOrganizationEntityToOrganizationModelResponse;
import it.gov.pagopa.reportingorgsenrollment.model.response.OrganizationModelResponse;

@Configuration
public class MappingsConfiguration {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        Converter<OrganizationEntity, OrganizationModelResponse> convertOrganizationEntityToOrganizationModelResponse = new ConvertOrganizationEntityToOrganizationModelResponse();
        mapper.createTypeMap(OrganizationEntity.class, OrganizationModelResponse.class).setConverter(convertOrganizationEntityToOrganizationModelResponse);
        
        return mapper;
    }

}
