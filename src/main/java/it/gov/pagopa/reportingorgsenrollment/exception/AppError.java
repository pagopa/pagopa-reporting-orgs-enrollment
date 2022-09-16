package it.gov.pagopa.reportingorgsenrollment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum AppError {

    ORGANIZATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Organization not found", "Not found the Organization Fiscal Code %s"),
    ORGANIZATION_DUPLICATED(HttpStatus.CONFLICT, "Organization with the specified organization fiscal code already exists in the system", "Already exists an organization with Organization Fiscal Code %s"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error enrollment processing ", "Error enrollment processing  [Organization Fiscal Code = %s]"),
    UNKNOWN(null, null, null);


    public final HttpStatus httpStatus;
    public final String title;
    public final String details;


    AppError(HttpStatus httpStatus, String title, String details) {
        this.httpStatus = httpStatus;
        this.title = title;
        this.details = details;
    }
}


