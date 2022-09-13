package it.gov.pagopa.reportingorgsenrollment.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.TableOperation;

import it.gov.pagopa.reportingorgsenrollment.entity.OrganizationEntity;
import it.gov.pagopa.reportingorgsenrollment.exception.AppException;
import it.gov.pagopa.reportingorgsenrollment.initializer.Initializer;
import lombok.extern.slf4j.Slf4j;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@ContextConfiguration(initializers = {Initializer.class})
@Slf4j
@SpringBootTest
class EnrollmentsServiceTests {

	@Autowired
	private EnrollmentsService enrollmentsService;
	
	
	@BeforeAll
	void setup() throws StorageException {
		Initializer.table.execute(TableOperation.insert(new OrganizationEntity("123456789", LocalDateTime.now().toString())));
		Initializer.table.execute(TableOperation.insert(new OrganizationEntity("abcdefghi", LocalDateTime.now().toString())));
	}
	
	@AfterAll
    void teardown() {
		CloudTable table = Initializer.table;
        if (null != table) {
        	try {
        		table.delete();
        		Initializer.azurite.stop();
        	} catch (Exception e) {
        		log.error("Error in teardown step", e);
            }
        }
    }
	
	@Test
	void createOrganization() {
		OrganizationEntity oe = enrollmentsService.createOrganization("987654321");
		assertEquals("987654321", oe.getRowKey());
		assertNotNull(oe.getOrganizationOnboardingDate());
		assertFalse(oe.getOrganizationOnboardingDate().isEmpty());
	}
	
	@Test
	void createOrganization_409() {
		try {
			// existing entity -> must raise a 409 exception
			enrollmentsService.createOrganization("123456789");
			fail();
		} catch (AppException e) {
			assertEquals(HttpStatus.CONFLICT, e.getHttpStatus());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void deleteOrganization() {
		enrollmentsService.deleteOrganization("abcdefghi");
		// This line means the call was successful
		assertTrue(true);
	}
	
	@Test
	void deleteOrganization_404() {
		try {
			// non-existent entity -> must raise a 404 exception
			enrollmentsService.deleteOrganization("non-existent");
			fail();
		} catch (AppException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getHttpStatus());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void getOrganization() {
		OrganizationEntity oe = enrollmentsService.getOrganization("123456789");
		assertEquals("123456789", oe.getRowKey());
		assertNotNull(oe.getOrganizationOnboardingDate());
		assertFalse(oe.getOrganizationOnboardingDate().isEmpty());
	}
	
	@Test
	void getOrganization_404() {
		try {
			// non-existent entity -> must raise a 404 exception
			enrollmentsService.getOrganization("non-existent");
			fail();
		} catch (AppException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getHttpStatus());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void getOrganizations() {
		Spliterator<OrganizationEntity> oe = enrollmentsService.getOrganizations();
		assertNotNull(oe);
		List<OrganizationEntity> oeList = StreamSupport.stream(oe, false).collect(Collectors.toList());
		assertTrue(oeList.size() >= 1);
	}
	
	
	

	
}
