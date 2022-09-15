package it.gov.pagopa.reportingorgsenrollment;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.gov.pagopa.reportingorgsenrollment.util.Constants;

@SpringBootTest
class ReportingOrgsEnrollmentApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true); // it just tests that an error has not occurred
	}

    @Test
    void applicationContextTest() {
    	ReportingOrgsEnrollmentApplication.main(new String[]{});
        assertTrue(true); // it just tests that an error has not occurred
    }
    
    @Test
    void constructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, SecurityException {
      Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
      assertTrue(Modifier.isPrivate(constructor.getModifiers()));
      assertNotNull(Constants.class.getDeclaredField("HEADER_REQUEST_ID"));
    }

}
