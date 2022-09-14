package it.gov.pagopa.reportingorgsenrollment.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Spliterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {
	
	private String mockOrganizationFiscalCode = "mockOrganizationFiscalCode";

	public static <T> T readModelFromFile(String relativePath, Class<T> clazz) throws IOException {
		ClassLoader classLoader = TestUtil.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource(relativePath)).getPath());
		var content = Files.readString(file.toPath());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper.readValue(content, clazz);
	}

	/**
	 * @param object to map into the Json string
	 * @return object as Json string
	 * @throws JsonProcessingException if there is an error during the parsing of
	 *                                 the object
	 */
	public String toJson(Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);
	}

	public static OrganizationEntity getMockOrganizationEntity() {

		return new OrganizationEntity(mockOrganizationFiscalCode, LocalDateTime.now().toString());
	}
	
	public static Spliterator<OrganizationEntity> getMockOrganizationEntitySpliterator() {
		ArrayList<OrganizationEntity> list = new ArrayList<>();
		list.add(new OrganizationEntity(mockOrganizationFiscalCode+"_1", LocalDateTime.now().toString()));
		list.add(new OrganizationEntity(mockOrganizationFiscalCode+"_2", LocalDateTime.now().toString()));
		list.add(new OrganizationEntity(mockOrganizationFiscalCode+"_3", LocalDateTime.now().toString()));
		return list.spliterator();
	}

	
	
}
