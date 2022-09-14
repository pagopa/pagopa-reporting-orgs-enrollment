package it.gov.pagopa.reportingorgsenrollment.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import it.gov.pagopa.reportingorgsenrollment.service.EnrollmentsService;
import it.gov.pagopa.reportingorgsenrollment.util.TestUtil;



@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentsControllerTests {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private EnrollmentsService enrollmentsService;
	
	@BeforeEach
    void setUp() {
        when(enrollmentsService.getOrganization(anyString())).thenReturn(TestUtil.getMockOrganizationEntity());
        when(enrollmentsService.createOrganization(anyString())).thenReturn(TestUtil.getMockOrganizationEntity());
        when(enrollmentsService.getOrganizations()).thenReturn(TestUtil.getMockOrganizationEntitySpliterator());
    }
	
	@Test
    void createOrganization() throws Exception {
        String url = "/organizations/mockOrganizationFiscalCode";
        MvcResult result = mvc.perform(post(url)
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().contains("mockOrganizationFiscalCode"));
    }
	
	@Test
    void getOrganization() throws Exception {
        String url = "/organizations/mockOrganizationFiscalCode";
        MvcResult result = mvc.perform(get(url)
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().contains("mockOrganizationFiscalCode"));
    }
	
	@Test
    void getOrganizations() throws Exception {
        String url = "/organizations";
        MvcResult result = mvc.perform(get(url)
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().contains("mockOrganizationFiscalCode_1"));
        assertTrue(result.getResponse().getContentAsString().contains("mockOrganizationFiscalCode_2"));
        assertTrue(result.getResponse().getContentAsString().contains("mockOrganizationFiscalCode_3"));
    }
	
	@Test
    void deleteECEnrollment() throws Exception {
        String url = "/organizations/mockOrganizationFiscalCode";
        MvcResult result = mvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().contains("was successfully removed"));
    }
  
}
