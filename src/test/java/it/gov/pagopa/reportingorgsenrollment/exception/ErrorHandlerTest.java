package it.gov.pagopa.reportingorgsenrollment.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import it.gov.pagopa.reportingorgsenrollment.ReportingOrgsEnrollmentApplication;
import it.gov.pagopa.reportingorgsenrollment.model.ProblemJson;


@SpringBootTest(classes = ReportingOrgsEnrollmentApplication.class)
class ErrorHandlerTest {

    @Autowired
    private ErrorHandler errorHandler;

    @Test
    void handleAppException() {
        AppException appException = new AppException(HttpStatus.BAD_REQUEST, "some error", "details", new NullPointerException());
        ResponseEntity<ProblemJson> actual = errorHandler.handleAppException(appException, null);
        assertEquals(appException.getHttpStatus(), actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(appException.getTitle(), actual.getBody().getTitle());
        assertEquals(appException.getMessage(), actual.getBody().getDetail());
        assertEquals(appException.getHttpStatus().value(), actual.getBody().getStatus());
    }

    @Test
    void handleAppException2() {
        AppException appException = new AppException(HttpStatus.BAD_REQUEST, "some error", "details", null);
        ResponseEntity<ProblemJson> actual = errorHandler.handleAppException(appException, null);
        assertEquals(appException.getHttpStatus(), actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(appException.getTitle(), actual.getBody().getTitle());
        assertEquals(appException.getMessage(), actual.getBody().getDetail());
        assertEquals(appException.getHttpStatus().value(), actual.getBody().getStatus());
    }

    @Test
    void handleGenericException() {
        ResponseEntity<ProblemJson> actual = errorHandler.handleGenericException(new NullPointerException("message"), null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals("INTERNAL SERVER ERROR", actual.getBody().getTitle());
        assertEquals("message", actual.getBody().getDetail());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actual.getBody().getStatus());
    }

    @Test
    void handleMissingServletRequestParameter() {
        ResponseEntity<Object> actual = errorHandler.handleMissingServletRequestParameter(new MissingServletRequestParameterException("param", "String"), null, null, null);
        assertNotNull(actual.getBody());
        ProblemJson body = (ProblemJson) actual.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertEquals("BAD REQUEST", body.getTitle());
        assertEquals("Required request parameter 'param' for method parameter type String is not present", body.getDetail());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
    }

    @Test
    void handleTypeMismatch() {
        MethodParameter methodParameter = Mockito.mock(MethodParameter.class);
        ResponseEntity<Object> actual = errorHandler.handleTypeMismatch(new MethodArgumentTypeMismatchException("2", String.class, "age", methodParameter, new IllegalArgumentException("")), null, null, null);
        assertNotNull(actual.getBody());
        ProblemJson body = (ProblemJson) actual.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertEquals("BAD REQUEST", body.getTitle());
        assertEquals("Invalid value 2 for property age", body.getDetail());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
    }
}
